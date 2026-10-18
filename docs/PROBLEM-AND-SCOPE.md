# Task 1 — Problem definition and scope

## Problem statement

Small pharmacies must keep frequently changing stock records accurate while serving customers. Paper registers and disconnected spreadsheets make it difficult to spot low stock, confirm availability quickly, and preserve a clear record of receipts and dispensing. The Pharmacy Inventory System MVP gives a staff member one catalogue view to find a product, update stock, and notice exceptions before placing a replenishment order.

## Users and stakeholders

| Stakeholder | Need |
|---|---|
| Pharmacist / counter staff | Find an item and confirm current quantity quickly; record stock movement accurately |
| Pharmacy owner / manager | Identify replenishment and expiry work; review operational changes |
| Supplier | Indirect stakeholder; receives replenishment orders outside this MVP |
| DevOps student / maintainer | Build, test, package, deploy, and recover a small web application |
| Customer | Indirect beneficiary of reliable availability; no customer or patient data is stored |

## Pain points and objectives

- Search catalogue by product name, SKU, category, or supplier.
- Record receive/dispense movements with a timestamped inventory ledger.
- Prevent negative stock and duplicate SKU records.
- Make low stock, zero stock, and expiry within 90 days visible.
- Deliver repeatable builds, tests, containers, and environment setup through automation.

## Measurable success criteria

1. A catalogue lookup returns a matching item within 2 seconds on a local laptop for the sample dataset.
2. A valid stock movement updates on-hand quantity and adds exactly one transaction entry.
3. Dispensing above available stock and duplicate SKU creation are rejected with inline guidance.
4. The catalogue exposes all four status filters and includes name, SKU, quantity, supplier, and expiry.
5. Maven tests and packaging complete successfully; Docker health check reports HTTP success after deployment.
6. Ansible can run twice without changing already-correct configuration on the second run.

## Constraints and assumptions

- Course/demo scope only; sample data is synthetic and local to the browser.
- No prescription, patient, payment, or personally identifiable data.
- No live procurement, authentication, permissions, server persistence, backup, or multi-user editing.
- A reachable Jenkins, Docker registry, GitHub remote, and target server are environment prerequisites, not bundled services.
- Expiry warning is a fixed 90-day operational threshold and needs owner confirmation before real use.

## Frozen 15-task MVP scope

The frozen plan is recorded in [TASK-PLAN.md](TASK-PLAN.md). Task 15 is reframed as a distinct release recovery and evidence task because the provided Task 15 duplicated Task 14. The feature MVP itself remains intentionally small: catalogue, create/update, stock/order status, search/filter, and exception alerts.
