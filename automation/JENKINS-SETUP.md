# Tasks 7–10 — Jenkins setup and operating notes

## Agent prerequisites

- Jenkins LTS with Pipeline, Git, GitHub integration (or multibranch pipeline), JUnit, and credentials-binding plugins.
- JDK 17+, Maven 3.9+, Python 3, Chrome/Chromium, Docker Engine, and permission to access the Docker socket on the build agent.
- Configure a GitHub webhook to trigger the multibranch pipeline, or use the Jenkinsfile's five-minute SCM polling fallback.

## Configure the job

1. Create a **Multibranch Pipeline** job and connect the GitHub repository URL using a read-only source credential.
2. Set script path to `Jenkinsfile`; enable branch discovery for `develop` and `main`.
3. Use an agent with the prerequisites above. The pipeline sets `CI=true`, launches the static demo app on port 18080 for Selenium, runs `mvn clean verify`, publishes Surefire XML, and archives the WAR.
4. Add `dockerhub-creds` as a Jenkins username/password credential only if image publishing is needed. The pipeline publishes only from `main`.
5. Set parameters `APP_VERSION`, `DEPLOY_TARGET`, and `APP_PORT`. For Tomcat deployment, configure `TOMCAT_WEBAPPS` and `APP_URL` in the job environment. For container deployment, confirm the agent can replace the running `pharmacy-inventory` container.
6. Capture the successful build URL, stage view, test report, and `target/pharmacy-inventory.war` artifact for course evidence.

## Test gate / defect exercise

The Docker build, publish, and deploy stages run only after `Build & Selenium` passes. To show a negative run, temporarily introduce an off-by-one change in the dispense guard in `app/app.js`; the Selenium test `rejectDispenseAboveAvailableStock` should fail and save a PNG under `target/failure-screenshots/`. Revert/fix the guard, commit the correction, and verify a green run before deployment. Do not deploy the intentionally defective build.

## Evidence recording

The repository contains pipeline and test configuration but does not claim a Jenkins job has run. Record the Jenkins controller URL, job name, build number, Git revision, stage result, artifact checksum, and test counts in your course report after running it on your lab controller.
