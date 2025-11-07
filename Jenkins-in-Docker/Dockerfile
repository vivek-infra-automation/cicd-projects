FROM jenkins/jenkins:lts-jdk17

USER root

RUN apt-get update && apt-get install -y \
    ca-certificates \
    curl \
    gnupg \
    lsb-release \
    git \
    vim \
    unzip \
    && mkdir -p /etc/apt/keyrings \
    && curl -fsSL https://download.docker.com/linux/debian/gpg | gpg --dearmor -o /etc/apt/keyrings/docker.gpg \
    && echo \
      "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] \
      https://download.docker.com/linux/debian \
      $(lsb_release -cs) stable" | tee /etc/apt/sources.list.d/docker.list > /dev/null \
    && apt-get update && apt-get install -y docker-ce-cli docker-compose-plugin \
    && curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip" \
    && unzip awscliv2.zip && ./aws/install && rm -rf awscliv2.zip aws/ \
    && apt-get clean && rm -rf /var/lib/apt/lists/*

RUN groupadd -f docker && usermod -aG docker jenkins


# Jenkins will auto-create it on first startup if not already initialized
ENV JENKINS_USER=admin
ENV JENKINS_PASS=123

# Copy Groovy init script to create admin user at startup
COPY init.groovy.d/ /usr/share/jenkins/ref/init.groovy.d/

USER jenkins
EXPOSE 8080
HEALTHCHECK --interval=1m --timeout=10s --start-period=30s --retries=3 \
  CMD curl -f http://localhost:8080/login || exit 1
