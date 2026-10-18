# GitHub collaboration workflow

This repository uses `main` for stable releases and `develop` for integration. Start feature work from `develop`, keep the change focused, and open a pull request back to `develop`.

```sh
git switch develop
git pull --ff-only origin develop
git switch -c feature/<short-name>
# make and review the change
git add <paths>
git commit -m "feat(scope): describe the change"
git push -u origin feature/<short-name>
```

In the pull request, summarize the user-visible change, list the acceptance checks, link any issue, and request a review. Merge only after review and required checks pass. After integration, merge `develop` into `main` through a release pull request and tag the release commit.

The local project history includes a catalogue-search feature branch, a review/merge sequence, a deliberately resolved conflict, and MVP tags. GitHub branch, review, and merge evidence is recorded separately because the local CLI has no configured GitHub credential.
