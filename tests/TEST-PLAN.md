# Tasks 9–10 — Selenium test plan

Run with Chrome/Chromium installed and `mvn -Dapp.baseUrl=http://127.0.0.1:8080 test`. The app must be served at the base URL. In CI, set `CI=true` to use headless Chrome. `InventoryJourneysTest` covers catalogue search/filter, create item, receive stock, and rejecting an invalid dispense. Each assertion is tied to user-visible rows, status, or inline error text. Failure screenshots are written to `target/failure-screenshots/` by the test teardown; Jenkins can archive this directory on failure.

Test data is synthetic and isolated in browser localStorage. The suite clears storage before each test. A red pipeline should stop before Docker deployment. To demonstrate the correction exercise, temporarily change the available-stock comparison in `app/app.js` from `n>x.qty` to `n>x.qty+1`, observe the invalid dispense journey fail, restore the comparison, commit the fix, and rerun the pipeline.
