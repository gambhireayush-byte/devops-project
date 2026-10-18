pipeline {
  agent any
  options { timestamps(); disableConcurrentBuilds() }
  triggers { pollSCM('H/5 * * * *') }
  parameters {
    string(name: 'APP_VERSION', defaultValue: '1.0.0', description: 'Version used for WAR and Docker image')
    choice(name: 'DEPLOY_TARGET', choices: ['container', 'tomcat', 'none'], description: 'Deployment destination')
    string(name: 'APP_PORT', defaultValue: '8080', description: 'Host port for container deployment')
  }
  environment {
    CI = 'true'
    IMAGE_NAME = 'pharmacy-inventory'
    APP_TEST_PORT = '18080'
    REGISTRY = 'docker.io'
    REGISTRY_CREDENTIALS = 'dockerhub-creds'
    TOMCAT_WEBAPPS = '/var/lib/tomcat10/webapps'
  }
  stages {
    stage('Checkout') { steps { checkout scm } }
    stage('Start test server') {
      steps { sh 'nohup python3 -m http.server ${APP_TEST_PORT} --directory app > /tmp/pharmacy-test-http.log 2>&1 & echo $! > /tmp/pharmacy-test-http.pid; for i in $(seq 1 20); do if curl -fsS http://127.0.0.1:${APP_TEST_PORT}/health; then exit 0; fi; sleep 1; done; echo "Test server did not become healthy"; exit 1' }
    }
    stage('Build & Selenium') {
      steps { sh 'mvn -B -Drevision=${params.APP_VERSION} -Dapp.baseUrl=http://127.0.0.1:${APP_TEST_PORT} clean verify' }
      post { always { junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'; archiveArtifacts allowEmptyArchive: true, artifacts: 'target/*.war' } }
    }
    stage('Build image') {
      when { expression { params.DEPLOY_TARGET == 'container' } }
      steps { sh 'docker build --build-arg APP_VERSION=${params.APP_VERSION} -t ${IMAGE_NAME}:${params.APP_VERSION} -t ${IMAGE_NAME}:latest .' }
    }
    stage('Publish image') {
      when { allOf { expression { params.DEPLOY_TARGET == 'container' }; branch 'main' } }
      steps { withCredentials([usernamePassword(credentialsId: "${REGISTRY_CREDENTIALS}", usernameVariable: 'REG_USER', passwordVariable: 'REG_PASS')]) { sh 'echo "$REG_PASS" | docker login ${REGISTRY} -u "$REG_USER" --password-stdin && docker tag ${IMAGE_NAME}:${params.APP_VERSION} ${REGISTRY}/${REG_USER}/${IMAGE_NAME}:${params.APP_VERSION} && docker push ${REGISTRY}/${REG_USER}/${IMAGE_NAME}:${params.APP_VERSION}' } }
    }
    stage('Deploy container') {
      when { expression { params.DEPLOY_TARGET == 'container' } }
      steps { sh 'docker rm -f pharmacy-inventory || true; docker run -d --name pharmacy-inventory --restart unless-stopped -p ${params.APP_PORT}:8080 ${IMAGE_NAME}:${params.APP_VERSION}; sleep 3; curl --fail http://127.0.0.1:${params.APP_PORT}/health' }
    }
    stage('Deploy Tomcat') {
      when { expression { params.DEPLOY_TARGET == 'tomcat' } }
      steps { sh 'install -m 0644 target/pharmacy-inventory.war ${TOMCAT_WEBAPPS}/ROOT.war && curl --retry 10 --retry-delay 3 --fail ${env.APP_URL ?: "http://127.0.0.1:8080"}/health' }
    }
  }
  post {
    always { sh 'if [ -f /tmp/pharmacy-test-http.pid ]; then kill $(cat /tmp/pharmacy-test-http.pid) || true; rm -f /tmp/pharmacy-test-http.pid; fi'; archiveArtifacts allowEmptyArchive: true, artifacts: 'target/failure-screenshots/**' }
    success { echo "Release ${params.APP_VERSION} passed tests and deployment checks." }
    failure { echo 'Pipeline failed. Deployment stages are gated by successful verification.' }
  }
}
