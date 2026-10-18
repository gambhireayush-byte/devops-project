# UX Contract

## Product boundary

The MVP is a local demonstration inventory tool for pharmacy staff and owners. It stores inventory in browser local storage and does not process prescriptions, patient records, payments, or real orders. Policy and operational decisions remain with the pharmacy.

## Canonical behavior

| Capability | Canonical owner | Source of truth | Allowed variants | Verification |
|---|---|---|---|---|
| CRUD | Catalogue and item dialog | UX contract | Create / edit / stock movement | Full browser journey |
| Form | Shared product and movement dialog forms | UX contract | Create / edit | Inline validation and first-error focus |
| Toast | Shared live-region status | UX contract | Success / error | Browser status announcement |
| Scrollbar | Global stylesheet | DESIGN.md | Visible / themed | Browser visual inspection |
| Select/Listbox | Native status and movement selects | UX contract | Native | Keyboard and filter assertion |
| Date | Native expiry input | UX contract | Native | Browser date entry |
| Catalogue search | Catalogue toolbar | UX contract | Local immediate search | Search + clear browser journey |
| Status filtering | Catalogue toolbar | UX contract | Native select | Filter result assertion |
| Create/edit product | Product dialog | UX contract | Create / edit | Inline validation and save journey |
| Stock transaction | Stock action dialog | UX contract | Receive / dispense | Movement and negative stock assertion |
| Feedback | Shared live-region toast | UX contract | Success / error | Status announcement check |

## Navigation and state

The left rail links to Overview, Inventory, Transactions, and Alerts. Current section is identified by text and a left rail. Search query and status are transient local state. Catalogue changes persist in local storage; the demo seed can be restored using Reset demo data.

## Safety and failure behavior

Inventory actions append an audit record with date, item, action, delta, and resulting stock. An invalid quantity or duplicate SKU is shown beside the field. Low-stock and expiry alerts are also represented in the table. This MVP provides no authentication, concurrency protection, cloud backup, role permissions, or regulated traceability guarantees.
