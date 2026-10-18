# Task 15 — Release and recovery handoff

- [ ] Freeze the backlog and review all acceptance criteria.
- [ ] Run `mvn clean verify`; retain Surefire report and WAR.
- [ ] Build and smoke-test the versioned Docker image.
- [ ] Confirm the image and source tag refer to the same commit.
- [ ] Capture deployment health response and container logs.
- [ ] Record the previous stable image tag and Ansible variables before rollout.
- [ ] Run Ansible twice; record first and second `changed` counts.
- [ ] Perform recovery to the previous tag and repeat health check.
- [ ] Save task screenshots in `screenshots/task-01` through `screenshots/task-15`.
- [ ] Tag `v1.0.0` only after the instructor reviews the evidence.

## Evidence index

The screenshot directories are deliberately empty until each step is executed on the learner's environment. A screenshot proves a visible state, not a remote GitHub merge, Jenkins run, Docker push, or server deployment. Keep corresponding console logs and URLs in the course submission when those services are available.
