# Ansible Linux target validation

Verified 2026-10-21 against a fresh Debian 12 target container attached to the local Docker Engine. Ansible installed target-side Python modules and support packages, created the pharmacy service account and release metadata directory, then deployed pharmacy-inventory:1.0.0 on host port 18083.

The Docker Engine and its service run on the container host and are exposed through /var/run/docker.sock. Since the target is a container without systemd, the playbook skips service management inside the target. This target-side run still verifies apt package setup, the service account, files, container deployment and application health.

## First provisioning run

    PLAY RECAP
    pharmacy-linux-target : ok=9 changed=4 unreachable=0 failed=0 skipped=2 rescued=0 ignored=0

The health request to http://host.docker.internal:18083/health returned HTTP 200 with status UP. The playbook wrote /var/lib/pharmacy-inventory/release.json with version 1.0.0 only after health passed.

## Idempotency run

The same inventory and playbook ran again without changing inputs:

    PLAY RECAP
    pharmacy-linux-target : ok=10 changed=0 unreachable=0 failed=0 skipped=1 rescued=0 ignored=0

## Recovery run

The playbook was run with app_version=deliberately-missing-release. The image pull returned 404; rescue restored the recorded 1.0.0 image and checked health. It then returned a failed-upgrade message so the caller knows the requested version was not deployed.

    PLAY RECAP
    pharmacy-linux-target : ok=9 changed=0 unreachable=0 failed=1 skipped=2 rescued=1 ignored=0

    Recorded release: {"service":"pharmacy-inventory","version":"1.0.0"}
    Health after recovery: {"status":"UP","service":"pharmacy-inventory"}

The recovery play exits nonzero by design: it reports the failed requested upgrade after verifying recovery. No pharmacy or patient data was used.
