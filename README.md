# Spring Boot + Docker + Kubernetes Deployment

A production-style deployment pipeline for a Java Spring Boot REST API using Docker, Kubernetes, and Nginx Ingress.

## Tech Stack

- Java 17 + Spring Boot
- Docker + Docker Hub
- Kubernetes (Minikube locally / EKS on AWS)
- Nginx Ingress Controller
- GitHub Actions CI/CD

## Architecture
User Request

│

▼

Nginx Ingress (demo.local)

│

▼

LoadBalancer Service

│

├──▶ Pod 1 (Spring Boot :8080)

├──▶ Pod 2 (Spring Boot :8080)

└──▶ Pod 3 (Spring Boot :8080)

│

├── ConfigMap (APP_PORT, PROFILE)

└── Secret (DB_PASSWORD, API_KEY)
## Quick Start

### Prerequisites
- Docker Desktop
- Minikube
- kubectl
- Java 17 + Maven

### Run Locally with Docker

```bash
docker pull abhinav0927/spring-demo:latest
docker run -p 8080:8080 abhinav0927/spring-demo:latest
curl http://localhost:8080
```

### Deploy to Kubernetes

```bash
# Start Minikube
minikube start --driver=docker --cpus=2 --memory=4096
minikube addons enable ingress
minikube tunnel

# Deploy
kubectl apply -f k8s/configmap.yaml
kubectl apply -f k8s/secret.yaml
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml
kubectl apply -f k8s/ingress.yaml

# Add to hosts file
echo "127.0.0.1 demo.local" | sudo tee -a /etc/hosts

# Test
curl http://demo.local
```

## API Endpoints

| Endpoint  | Method | Response                  |
|-----------|--------|---------------------------|
| /         | GET    | Hello from Kubernetes!    |
| /health   | GET    | OK                        |

## Kubernetes Objects Used

| Object      | Purpose                                      |
|-------------|----------------------------------------------|
| Deployment  | Manages 3 replicas with rolling updates      |
| ReplicaSet  | Ensures pods are always running              |
| Service     | LoadBalancer exposing pods internally        |
| Ingress     | Routes demo.local traffic via Nginx          |
| ConfigMap   | Injects non-sensitive environment variables  |
| Secret      | Injects sensitive credentials (base64)       |

## CI/CD Pipeline

Every push to `main` triggers GitHub Actions to:
1. Build the JAR with Maven
2. Build the Docker image
3. Push to Docker Hub automatically

## Key Kubernetes Commands

```bash
kubectl get pods                          # List all pods
kubectl scale deployment spring-demo --replicas=5   # Scale up
kubectl rollout undo deployment/spring-demo         # Rollback
kubectl logs <pod-name>                   # View logs
```

## Author

Abhinav Gaikwad — [GitHub](https://github.com/abhinav0927)
