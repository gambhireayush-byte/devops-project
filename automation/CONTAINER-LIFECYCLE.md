# Tasks 11–14 — Container and target operations

## Build and inspect locally

```sh
mvn -B clean package
docker build --build-arg APP_VERSION=1.0.0 -t pharmacy-inventory:1.0.0 .
docker image inspect pharmacy-inventory:1.0.0
docker run -d --name pharmacy-inventory -p 8080:8080 pharmacy-inventory:1.0.0
docker logs -f pharmacy-inventory
curl --fail http://localhost:8080/health
docker stop pharmacy-inventory
docker start pharmacy-inventory
docker rm -f pharmacy-inventory
```

The Maven WAR is served by Tomcat. For production use, create a least-privilege runtime user, pin base images by digest, scan images, and supply registry credentials via Jenkins credentials.

## Jenkins registry and deployment run

The Jenkins pipeline uses a loopback-only registry at `127.0.0.1:5000`, so the lab run does not require Docker Hub credentials. Build #5 built image `pharmacy-inventory:1.0.0`, pushed it as `127.0.0.1:5000/pharmacy-inventory:1.0.0`, pulled that tag, and deployed a fresh container. The `/health` response was `UP` at host port `18081`. The registry is local to the Docker host and is for demonstration only; use an authenticated remote registry for shared environments.

Lifecycle validation used a separate `pharmacy-lifecycle-test` container: run, inspect, logs, health check, stop, restart, health check again, then remove. The Jenkins deployment container remains running as `pharmacy-inventory`.

## Ansible configuration specification

| Resource | Desired state |
|---|---|
| Packages | Docker engine, curl, CA certificates |
| Service | Docker enabled and running |
| User | SSH automation account with controlled sudo on lab host |
| Folders/files | `/opt/pharmacy-inventory` root-owned 0755; `release.json` root-owned 0644 |
| Image | `pharmacy-inventory:<app_version>` already present on the target or available in a configured registry |
| Network | inbound 8080/TCP allowed by host/cloud firewall |
| Service/container | `pharmacy-inventory`, restart unless stopped, image/version variables |

Install the required module with `ansible-galaxy collection install -r automation/ansible/requirements.yml`. Run `ansible-playbook -i automation/ansible/inventory.ini automation/ansible/site.yml --check --diff` to preview, then omit `--check` to apply. The example IP is documentation-only and must be replaced. Verify idempotency by running a second time and confirming `changed=0` when inputs have not changed. Roll back by setting `app_version` to the previous known-good release/image tag and re-running the playbook; verify `/health` and the version label. A real recovery drill must retain the previous image and config before deployment.

The playbook stores the version only after the new container passes its health check. If deployment or health validation fails, it restores the last recorded version and checks that release's health before reporting the failed upgrade. The first deployment has no previous version to restore, so a failed new container is removed. For a manual rollback, set `app_version` to the last known-good image tag and run the playbook again. The target must have the current and previous images available from the configured registry or local Docker cache.

Local execution evidence: Ansible first run completed with `ok=6 changed=3 failed=0`; the unchanged rerun reported `ok=7 changed=0 failed=0`. An invalid image tag caused the upgrade to fail and invoked recovery; the previous `1.0.0` container returned health 200. These runs were on localhost/macOS. The playbook skips Linux package, service and user setup on Darwin, so this is not evidence of provisioning a clean Linux node.
