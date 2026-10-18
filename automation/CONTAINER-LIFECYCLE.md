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

## Jenkins registry configuration

Create a Jenkins username/password credential named `dockerhub-creds`, set `REGISTRY` and repository path for your account, and allow the agent to access Docker. The Jenkinsfile only publishes from `main`; fork builds do not receive registry credentials. Use build parameter `APP_VERSION` for release tags.

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
