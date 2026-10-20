pipeline {
  agent any
  options { timestamps(); disableConcurrentBuilds() }
  triggers { pollSCM('H/5 * * * *') }
  parameters {
    string(name: 'APP_VERSION', defaultValue: '1.0.0', description: 'Version used for WAR and Docker image')
    choice(name: 'DEPLOY_TARGET', choices: ['container', 'tomcat', 'none'], description: 'Deployment destination')
    string(name: 'APP_PORT', defaultValue: '8080', description: 'Host port for container deployment')
    string(name: 'APP_URL', defaultValue: 'http://127.0.0.1:8080', description: 'Tomcat deployment health-check URL')
  }
  environment {
    CI = 'true'
    IMAGE_NAME = 'pharmacy-inventory'
    APP_TEST_PORT = '18080'
    REGISTRY = 'docker.io'
    TOMCAT_WEBAPPS = '/var/lib/tomcat10/webapps'
    APP_TEST_URL = 'http://127.0.0.1:18080'
    PATH = "/opt/homebrew/bin:${env.PATH}"
  }
  stages {
    stage('Checkout') { steps { checkout scm } }
    stage('Start test server') {
      steps {
        sh '''
          nohup python3 -m http.server "$APP_TEST_PORT" --directory app > /tmp/pharmacy-test-http.log 2>&1 &
          echo $! > /tmp/pharmacy-test-http.pid
          for i in $(seq 1 20); do
            if curl -fsS "$APP_TEST_URL/health"; then exit 0; fi
            sleep 1
          done
          echo "Test server did not become healthy"
          exit 1
        '''
      }
    }
    stage('Build & Selenium') {
      steps { sh 'mvn -B -Drevision="$APP_VERSION" -Dapp.baseUrl="$APP_TEST_URL" clean verify' }
      post { always { junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'; archiveArtifacts allowEmptyArchive: true, artifacts: 'target/*.war' } }
    }
    stage('Build image') {
      when { expression { params.DEPLOY_TARGET == 'container' } }
      steps { sh 'docker build --build-arg APP_VERSION="$APP_VERSION" -t "$IMAGE_NAME:$APP_VERSION" -t "$IMAGE_NAME:latest" .' }
    }
    stage('Publish image') {
      when { allOf { expression { params.DEPLOY_TARGET == 'container' }; branch 'main' } }
      steps {
        withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'REG_USER', passwordVariable: 'REG_PASS')]) {
          sh 'echo "$REG_PASS" | docker login "$REGISTRY" -u "$REG_USER" --password-stdin && docker tag "$IMAGE_NAME:$APP_VERSION" "$REGISTRY/$REG_USER/$IMAGE_NAME:$APP_VERSION" && docker push "$REGISTRY/$REG_USER/$IMAGE_NAME:$APP_VERSION"'
        }
      }
    }
    stage('Deploy container') {
      when { expression { params.DEPLOY_TARGET == 'container' } }
      steps {
        sh '''
          docker rm -f pharmacy-inventory || true
          docker run -d --name pharmacy-inventory --restart unless-stopped -p "$APP_PORT:8080" "$IMAGE_NAME:$APP_VERSION"
          for i in $(seq 1 20); do
            if curl --fail "http://127.0.0.1:$APP_PORT/health"; then exit 0; fi
            sleep 1
          done
          echo "Container health check failed"
          exit 1
        '''
      }
    }
    stage('Deploy Tomcat') {
      when { expression { params.DEPLOY_TARGET == 'tomcat' } }
      steps { sh 'install -m 0644 target/pharmacy-inventory.war "$TOMCAT_WEBAPPS/ROOT.war" && curl --retry 10 --retry-delay 3 --fail "$APP_URL/health"' }
    }
  }
  post {
    always { sh 'if [ -f /tmp/pharmacy-test-http.pid ]; then kill $(cat /tmp/pharmacy-test-http.pid) || true; rm -f /tmp/pharmacy-test-http.pid; fi'; archiveArtifacts allowEmptyArchive: true, artifacts: 'target/failure-screenshots/**' }
    success { echo "Release ${params.APP_VERSION} passed tests and deployment checks." }
    failure { echo 'Pipeline failed. Deployment stages are gated by successful verification.' }
  }
}
