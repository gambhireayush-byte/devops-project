# Execution status and evidence boundary

This workspace contains the application and automation definitions. The local app has been packaged with Maven and the Selenium journeys have run successfully. A temporary Jenkins LTS controller is running locally; the remaining pipeline and infrastructure stages still need their live execution evidence.

| Task range | Prepared locally | Live evidence status |
|---|---|---|
| 1–3 | Scope, backlog, SRS, diagrams, data/API model, local app | App opened in the in-app browser and key user flows exercised |
| 4–6 | Local Git branches, feature commits, merge conflict and resolution, issue templates, local tags through `v0.1.3-mvp` | [GitHub repository](https://github.com/gambhireayush-byte/devops-project) created with README and project files; `develop` branch created; [PR #1](https://github.com/gambhireayush-byte/devops-project/pull/1) reviewed with a comment and merged into `develop`; [v0.1.3-mvp GitHub release](https://github.com/gambhireayush-byte/devops-project/releases/tag/v0.1.3-mvp) published from `main` |
| 7–8 | Maven WAR, Jenkinsfile, Jenkins LTS controller and suggested plugins | Maven `clean verify` built `target/pharmacy-inventory.war`; Jenkins 2.568.3 is running at `http://127.0.0.1:8090/`; pipeline job and deployment run are still pending |
| 9–10 | Four Selenium journeys, failure screenshot handler, Jenkins test gate | Local Maven/Selenium run passed: 4 tests, 0 failures; Jenkins publication/gating and a deliberate-defect rerun are still pending |
| 11–12 | Tomcat Dockerfile, lifecycle instructions, Docker image pipeline | Docker CLI is present, but the Docker daemon is not running; image build, registry publication, and container deployment are not executed |
| 13–14 | Ansible inventory, requirements, idempotent container playbook, health check, and recovery block | Playbook now records the last healthy version and attempts recovery on failed deployment; Ansible is not installed and the target host is a documentation placeholder, so it is not executed |
| 15 | Release checklist and recovery instructions | The GitHub release records the source baseline only; it does not claim successful CI, registry publication, or deployment |

The 15 `screenshots/task-NN/` folders have been created and are reserved for screenshots only. Browser captures were reviewed in the in-app browser, but no screenshot image files have been written into those folders. Add the actual screenshot files after opening each task's evidence state on the configured lab services. Do not represent a local configuration file as a successful remote deployment.
