# Strimzi Playground

## What are places that you should mention the Strimzi/Kafka versions?
1. [Kafka Connect Dockerfile](build-connect/Dockerfile)
2. [Helm chart parent values file](helm-charts/values.yaml)

## Steps:
1. Prerequesties: Kubernetes Cluster / Kubectl 
2. create a new namespace in the k8s
   ```bash
   kubectl create namespace kafka
   ```
3. install strimzi operator in the cluster/namespace
   ```bash
   kubectl create -f 'https://strimzi.io/install/latest?namespace=kafka' -n kafka
   ```
4. install the prometheus operators.
   ```bash
   kubectl create -f https://raw.githubusercontent.com/prometheus-operator/prometheus-operator/master/bundle.yaml -n kafka
   ```
5. build the kafka connect docker image
6. build docker images for the producer, consumer and other utility applications
7. deploy the helm chart