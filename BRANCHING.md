# Branching and review rules

- `main`: release-ready; protect it on GitHub with pull requests, one review, and passing CI.
- `develop`: integration branch for course feature work.
- `feature/<short-name>`, `bugfix/<short-name>`, `docs/<short-name>`, `release/<version>`.
- Keep branches focused and rebase/merge the current `develop` before review.
- Use Conventional Commit style: `feat|fix|docs|test|build|ci|chore(scope): summary`.
- Require PR checklist, linked issue, build/test evidence, and no secrets or sensitive data.
