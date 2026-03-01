# 1. Update the package index
sudo yum update -y

# 2. Install Docker
sudo yum install docker -y

# 3. Start and Enable Docker service
sudo systemctl start docker
sudo systemctl enable docker

# 4. Install Docker Compose (Modern Plugin version)
sudo mkdir -p /usr/local/lib/docker/cli-plugins/
sudo curl -SL https://github.com/docker/compose/releases/latest/download/docker-compose-linux-x86_64 -o /usr/local/lib/docker/cli-plugins/docker-compose
sudo chmod +x /usr/local/lib/docker/cli-plugins/docker-compose

# 5. Add your user and Jenkins to the docker group
sudo usermod -aG docker $USER
sudo usermod -aG docker jenkins

# 6. Final verification (Run this after logging out and back in)
docker compose version