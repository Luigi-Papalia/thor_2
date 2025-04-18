#!/bin/bash

# Nome dell'immagine e del deployment
IMAGE_NAME="microservizio-java"
IMAGE_TAG="v1"
APP_LABEL="microservizio-java"

# Step 1: Configura l'ambiente Docker per Minikube
echo "Configurazione ambiente Docker per Minikube..."
eval $(minikube docker-env)

# Step 2: Compila l'applicazione Java
echo "Compilazione del progetto Java..."
./mvnw clean package || { echo "Errore durante la compilazione"; exit 1; }

# Step 3: Costruzione dell'immagine Docker
echo "Costruzione dell'immagine Docker..."
docker build -t ${IMAGE_NAME}:${IMAGE_TAG} . || { echo "Errore durante la build Docker"; exit 1; }

# Step 4: Elimina il pod esistente (verrà ricreato da Kubernetes)
echo "Eliminazione del pod esistente..."
kubectl delete pod -l app=${APP_LABEL} --ignore-not-found

# Step 5: Attendi che il nuovo pod venga creato
echo "Attesa del nuovo pod..."
kubectl wait --for=condition=ready pod -l app=${APP_LABEL} --timeout=90s

# Step 6: Mostra lo stato dei pod
echo "Stato dei pod attuali:"
kubectl get pods -l app=${APP_LABEL}

# Step 7: Mostra il NodePort del servizio
echo "Accesso tramite NodePort:"
kubectl get svc ${APP_LABEL}-service

# Step 8: Mostra l’IP di Minikube
MINIKUBE_IP=$(minikube ip)
echo "IP Minikube: ${MINIKUBE_IP}"

# Opzionale: costruiamo l'URL se la porta è nota
NODE_PORT=$(kubectl get svc ${APP_LABEL}-service -o=jsonpath='{.spec.ports[0].nodePort}')
echo "Endpoint accessibile: http://${MINIKUBE_IP}:${NODE_PORT}/api/users/find?name=Alice"
