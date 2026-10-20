# Project execution status and assessment notes

Status last verified: 2026-10-21. This report distinguishes prepared artifacts from work that was actually executed. Percentages are completion estimates against each task's stated deliverable, not marks awarded by an assessor.

| Task | Estimate | Status and evidence |
|---|---:|---|
| 1. Problem definition and scope | 100% | Problem statement, users, stakeholders, pain points, constraints, success criteria and a 15-task MVP scope are documented. |
| 2. Agile planning and DevOps workflow | 100% | User stories, acceptance criteria, backlog, 15-task plan, Definition of Done and lifecycle diagram are documented. |
| 3. Requirements, architecture and setup | 100% | SRS summary, use cases, architecture, data/API design, stack and local application are present; app was opened and exercised locally. |
| 4. GitHub repository initialization | 100% | Public [GitHub repository](https://github.com/gambhireayush-byte/devops-project), README, ignore rules, templates and development branch are present. |
| 5. Feature branch and pull request | 100% | [PR #1](https://github.com/gambhireayush-byte/devops-project/pull/1) was reviewed and merged into `develop`; merge evidence is on GitHub. |
| 6. MVP and collaboration | 100% | Catalogue, transactions, stock/order status, search and alerts are implemented. Branching/conflict resolution and the [v0.1.3-mvp release](https://github.com/gambhireayush-byte/devops-project/releases/tag/v0.1.3-mvp) are documented. |
| 7. Jenkins CI | 100% | Jenkins 2.568.3 job polls the GitHub feature branch. Build #5 passed Maven packaging and all four Selenium tests; JUnit results and WAR artifact were published. |
| 8. Pipeline and deployment | 100% | Jenkinsfile checks out, builds, tests, packages and deploys a parameterized container. Build #5 completed successfully and health returned `UP` at `http://127.0.0.1:18081/`. |
| 9. Selenium design and local run | 100% | Four critical browser journeys are automated; local Maven execution passed 4/4 and failure screenshots are configured. |
| 10. Continuous testing in Jenkins | 75% | Jenkins runs Selenium tests, publishes JUnit reports, and stops deploy on failure. A packaging defect in `.dockerignore` was corrected and the pipeline rerun successfully. The rubric's separately *deliberately introduced* application defect and correction evidence has not yet been captured. |
| 11. Docker lifecycle | 100% | Image was built; a separate container was run, inspected, logged, stopped, restarted, health-checked and removed. Lifecycle steps are documented. |
| 12. Jenkins-Docker deployment | 100% | Build #5 built and tagged `1.0.0`, pushed to a loopback-only local registry, pulled the tag, deployed a fresh container and passed its health check. |
| 13. Configuration management | 80% | Ansible playbook and prerequisites are present and executed against localhost. It provisions release metadata and the Docker container. On macOS, Linux package, service and user setup are skipped; a clean Linux target provisioning run remains. |
| 14. Provisioning and reliability | 85% | Local Ansible application succeeded; an unchanged second run reported `changed=0`. An invalid image-tag upgrade was rejected and the playbook restored the known-good `1.0.0` release with health 200. A clean target-node run remains. |
| 15. Final validation / duplicate item | 85% | The prompt duplicates task 14. The recovery and health-check evidence is complete locally; the clean target requirement is shared with task 14. |

## Verifiable run details

- Jenkins: `http://127.0.0.1:8090/`; successful pipeline build: `http://127.0.0.1:8090/job/pharmacy-inventory-ci/5/consoleText`.
- Deployed local app: `http://127.0.0.1:18081/`; health endpoint: `/health`.
- Local registry: `127.0.0.1:5000`; repository `pharmacy-inventory`, tag `1.0.0`.
- Selenium: Maven `clean verify`, 4 tests, 0 failures.
- Ansible: first run `ok=6 changed=3 failed=0`; unchanged rerun `ok=7 changed=0 failed=0`; invalid-tag recovery restored the prior image and health check returned 200. Recovery run's nonzero result is expected because the requested upgrade was intentionally bad.
- Docker lifecycle used temporary container `pharmacy-lifecycle-test`; it was removed after stop/restart checks. The deployed `pharmacy-inventory` container remains available on port 18081.

## Remaining assessment gaps

1. Capture and save screenshots from Jenkins, GitHub, the app, Docker and Ansible into `screenshots/task-01/` through `screenshots/task-15/`. The directories exist, but browser captures have not yet been saved as image files there.
2. For task 10, deliberately introduce a small application defect, show Jenkins failing the test gate, fix it in a commit, and show the successful rerun.
3. Run the Ansible configuration against a clean Linux VM/target to demonstrate package, service, user and firewall prerequisites. Current Ansible execution used localhost on macOS, so those Linux-specific tasks were skipped.
4. The latest CI/Ansible updates are on the open [PR #2](https://github.com/gambhireayush-byte/devops-project/pull/2); it still needs review and merge into `develop`.

Task 15 in the assignment repeats task 14. For assessment, describe task 15 as the final acceptance/reliability validation and explain that it shares the provisioning and recovery evidence with task 14.
