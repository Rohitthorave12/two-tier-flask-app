# Two-Tier Flask Application: Automated CI/CD Pipeline

This project demonstrates a production-ready CI/CD pipeline for a Flask-MySQL application. I architected the infrastructure to automate the deployment process using Jenkins and Docker.

## 🚀 The Architecture
The setup consists of:
1. **Frontend/Backend:** Flask Application (Python).
2. **Database:** MySQL 8.0.
3. **Orchestration:** Docker Compose for multi-container management.
4. **CI/CD:** Jenkins Pipeline automating the build, pull, and deployment.



## 🛠️ Infrastructure & DevOps (My Focus)
While the application logic was provided by a collaborator, I was responsible for the entire deployment infrastructure:

* **Containerization:** Wrote the Dockerfiles and managed multi-container networking via `my-network`.
* **Database Persistence:** Implemented Docker Volumes to ensure MySQL data persists across restarts.
* **Health Checks:** Configured Docker Healthchecks to ensure the Flask container only starts after the MySQL database is fully ready.
* **Automation:** Developed a Groovy-based Jenkinsfile for seamless "git push to deploy" workflow.

## 🔧 Troubleshooting & Linux Administration
During development, I resolved critical system-level issues:
* **Binary Path Resolution:** Fixed symbolic link errors where `docker-compose` was inaccessible to the Jenkins user.
* **Permission Management:** Configured Linux user groups and execution bits (`chmod 755`) to allow the Jenkins service to control the Docker engine securely.

## Infrastructure & Debugging Logs

🛡️ Troubleshooting & System Engineering
During the setup of this pipeline on an AWS EC2 (Amazon Linux/RHEL) instance, I encountered and resolved several production-level hurdles:
1. Command Resolution Issues
Problem: Jenkins reported docker-compose: command not found (Exit Code 127).
Discovery: Used whereis and ls -l to find that the binary was a symbolic link pointing to a restricted /root/.docker/ directory.
Fix: Removed the link and moved the physical binary to /usr/local/bin/docker-compose, a location accessible to service users.
2. Linux Permission Constraints
Problem: Pipeline failed with Permission denied (Exit Code 126).
Discovery: Identified that the jenkins user lacked execution bits for the binary and was not part of the docker group.
Fix:
Applied chmod 755 to the binary.
Added the Jenkins user to the Docker group using usermod -aG docker jenkins.
Restarted the Jenkins service to apply the environment changes.

3. Container Race Conditions
Problem: The Flask application failed to start because it attempted to connect to MySQL before the database was fully initialized.

Fix: Implemented a Docker Healthcheck in the Compose file and used the service_healthy condition in the depends_on block to ensure a sequenced startup.

## 📖 How to Use
1. Clone the repo.
3. Ensure Docker and Jenkins are installed.
4. Configure Jenkins with Docker Hub credentials (`docker-hub-creds`).
5. Run the pipeline!
