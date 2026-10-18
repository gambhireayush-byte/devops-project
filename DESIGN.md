---
version: alpha
colors:
  ink: "#172A2A"
  forest: "#174F46"
  mint: "#DDF2E7"
  paper: "#F5F7F4"
  white: "#FFFFFF"
  amber: "#A96316"
  coral: "#B64F42"
typography:
  display:
    fontFamily: "Georgia, 'Times New Roman', serif"
  sans:
    fontFamily: "Inter, ui-sans-serif, system-ui, -apple-system, sans-serif"
  mono:
    fontFamily: "'SFMono-Regular', Consolas, monospace"
rounded:
  card: "12px"
  control: "8px"
spacing:
  unit: "4px"
components:
  sidebar:
    width: "232px"
  table:
    density: "comfortable"
---

## Overview

The Pharmacy Inventory System is a product surface for a neighborhood pharmacy team that needs to find stock quickly and act safely during a busy counter shift. The interface should feel like a well-kept clinical stock ledger: calm, legible, and operational. The product register leads; expressive typography is reserved for the page thesis and key totals.

The signature is the evergreen stock-health rail: one continuous, compact signal that makes availability and urgency visible without turning every row into an alarm. Avoid generic hospital dashboards, glowing gradients, and decorative medical crosses.

## Colors

Forest and ink carry navigation and primary action. Mint marks healthy inventory. Amber identifies stock needing attention; coral is reserved for expired or unavailable items. Paper is the application canvas and white is the working surface. Status is always paired with text, never color alone.

## Typography

Georgia gives the page title a quiet ledger quality. System sans is used for controls, body copy, and tables for familiar cross-platform rendering. Monospace is reserved for SKU, batch, and audit identifiers. Maintain readable line-height and allow long names to wrap on narrow screens.

## Layout

At laptop width, use a fixed-width navigation rail and a fluid content region with a twelve-column rhythm. Keep the catalogue dense enough to scan while preserving generous row height for touch and hurried use. Below 900px the rail collapses into a top navigation strip; below 640px data cards replace wide table rows.

## Elevation & Depth

Use thin green-grey borders and a shallow shadow only for floating panels. Avoid nested cards. The main working surface stays flat and stable.

## Shapes

Cards use 12px corners and controls use 8px corners. Pills are fully rounded only for compact statuses. Avoid oversized rounded containers.

## Components

Primary actions use forest fill with white text. Search and form controls use white fill, a visible border, and a strong keyboard focus ring. Alerts use semantic icon, label, and quantity. Motion is short and functional; respect reduced-motion preferences. Scrollbars remain visible and use the application palette.

## Do's and Don'ts

- Keep the catalogue, stock health, and next action visible at the top of the work area.
- Use concrete drug, pack, batch, and supplier language; do not imply the demo is connected to a real pharmacy.
- Never communicate expiry or low stock with color alone.
- Do not expose personal health information; this MVP tracks inventory only.
