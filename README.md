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
5. [Optional] build the kafka connect docker image. If you don't build - it will pull the mentioned Kafka Connect version.
   ```bash
   cd apps/kafka-plugins/connectors/weatherapi-connector
   make
   cd <build-connect>
   ```
   Now, Change the Dockerfile (base Kafka verison) and Makefile(DOCKER_TAG, DOCKER_VERSION_ARG) based on your need. 
   ```bash
   make
   ```
6. [Optional] Build docker images for the producer, consumer and other utility applications
7. deploy the helm chart
   ```bash
   cd helm-charts
   make
   ```
8. When you are finished, you can uninstall the entire deployment with the following:
   ```bash
   helm uninstall demo-kafka -n kafka
   ```