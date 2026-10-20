# Project execution status and assessment notes

Verified 2026-10-21. Percentages estimate completion against the assignment deliverables; they are not assessment marks.

| Task | Complete | Status |
|---|---:|---|
| 1. Problem definition and scope | 100% | Problem statement, users, stakeholders, pain points, constraints, success measures and 15-task MVP documented. |
| 2. Agile planning and DevOps workflow | 100% | Stories, acceptance criteria, backlog, task plan, Definition of Done and lifecycle diagram documented. |
| 3. Requirements, architecture and setup | 100% | SRS, use cases, architecture, data/API design and local app completed. |
| 4. GitHub repository initialization | 100% | [Repository](https://github.com/gambhireayush-byte/devops-project), README, ignore rules, templates and branch policy available. |
| 5. Feature branch and PR | 100% | [PR #1](https://github.com/gambhireayush-byte/devops-project/pull/1) reviewed and merged into develop. |
| 6. MVP and Git collaboration | 100% | Catalogue, transactions, stock status, search, alerts, branch/conflict exercise and v0.1.3-mvp release completed. |
| 7. Jenkins CI | 100% | Jenkins job polls the repository; build #10 packaged the WAR, passed Selenium, and published reports/artifacts. |
| 8. Pipeline and deployment | 100% | Jenkinsfile builds and deploys a parameterized container; build #10 health check passed at localhost:18081. |
| 9. Selenium design and local run | 100% | Four critical journeys run locally; 4/4 passed and failure screenshots are configured. |
| 10. Continuous testing | 100% | Build #9 failed at the Selenium gate after a deliberate search defect; correction was committed; build #10 passed and deployed. |
| 11. Docker lifecycle | 100% | Image built; separate container run, inspected, logged, stopped, restarted, health-checked and removed. |
| 12. Jenkins-Docker deployment | 100% | Version 1.0.0 published to the loopback registry, pulled, deployed fresh and health-checked. |
| 13. Configuration management | 95% | Ansible ran on a fresh Debian 12 target container, installed support packages, created the service account and release directory, and deployed the app. The host supplies Docker Engine through its socket, so there is no systemd Docker service inside the target container. |
| 14. Provisioning and reliability | 95% | First Linux-target run passed and returned health 200; unchanged rerun was changed=0; missing-tag rollout recovered 1.0.0 and health 200. |
| 15. Final validation (duplicate of 14) | 95% | Same isolated-target provisioning, idempotency, health and recovery evidence applies to this repeated assignment item. |

## Run evidence

- Jenkins success: [build #10](http://127.0.0.1:8090/job/pharmacy-inventory-ci/10/consoleText); deliberate Selenium gate failure: [build #9](http://127.0.0.1:8090/job/pharmacy-inventory-ci/9/consoleText).
- Application: http://127.0.0.1:18081/; health endpoint /health.
- Local image registry: 127.0.0.1:5000; image pharmacy-inventory:1.0.0.
- Selenium: Maven clean verify, 4 tests, 0 failures.
- Debian Ansible: initial ok=9 changed=4 failed=0; unchanged rerun ok=10 changed=0 failed=0; recovery rescued=1, intentionally nonzero because the requested missing release failed, but prior version health was 200.
- Detailed Linux target results: docs/evidence/ANSIBLE-LINUX-VALIDATION.md.

## Remaining evidence work

The screenshots/task-01/ through screenshots/task-15/ folders exist, but screenshot image files have not yet been saved into them. Capture UI/terminal proof into those folders before final assessment submission. Task 15 repeats task 14 in the assignment and should be presented as shared final acceptance evidence.

The CI and provisioning work is on [PR #2](https://github.com/gambhireayush-byte/devops-project/pull/2); merge it into develop after reviewing the latest branch state.
