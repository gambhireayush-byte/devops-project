# Pharmacy Inventory System

A DevOps course project demonstrating an automated delivery workflow for a small pharmacy inventory MVP. The local application is a browser-based prototype; it tracks catalogue items, stock movements, availability, search/filter, and operational alerts without storing patient data.

## Run locally

Open `app/index.html` in a browser, or serve the project root with `python3 -m http.server 8080` and visit `http://localhost:8080/app/`. Data persists in that browser's local storage. Use **Reset demo data** to restore the sample catalogue.

## MVP functions

- Search by product name, SKU, category, or supplier and filter by stock status.
- Create and edit catalogue items.
- Receive or dispense stock with validation and an audit trail.
- Monitor low stock, expiry, and out-of-stock exceptions.
- Responsive layout and local demo data.

## Project structure

```text
app/                 Browser MVP
docs/                SRS, architecture, API, and operating guides
automation/          Jenkins, Docker, and Ansible examples
tests/               Selenium test plan and starter suite
screenshots/task-01/ Evidence captured for task 1 (one folder per task)
```

## DevOps scope

The repository includes a 15-task course plan, sample Jenkins pipeline, Dockerfile, Ansible playbook, test automation starter, issue templates, and release workflow. External infrastructure steps (GitHub remote, Jenkins server, registry, and deployment host) require credentials and a reachable target; the included files are ready to configure for those services.

## Demo disclaimer

This is an educational prototype, not a production pharmacy system. It has no authentication, authorization, server-side persistence, multi-user concurrency, backup, or regulatory controls. Do not enter real pharmacy, patient, or prescription data.

## Branches and commits

Use `main` for stable releases and `develop` for integration. Name branches `feature/<short-name>`, `bugfix/<short-name>`, `docs/<short-name>`, or `release/<version>`. Commit format: `type(scope): imperative summary` (for example, `feat(inventory): add stock movement dialog`). Protect `main` with pull-request review and a passing pipeline.
