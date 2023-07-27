# Strimzi Playground

- [Strimzi Playground](#strimzi-playground)
  - [Knowledge](#knowledge)
  - [What is happening here?](#what-is-happening-here)
  - [Deployent Steps](#deployent-steps)
    - [What are places that you should mention the Strimzi/Kafka versions?](#what-are-places-that-you-should-mention-the-strimzikafka-versions)
    - [Todo](#todo)
    - [Interaction](#interaction)

## Knowledge

1. [What is Kafka?](./docs/KAFKA.md)
2. [Kafka Flavors](./docs/FLAVORS.md)
3. [What is Strimzi?](https://strimzi.io/)
4. [Key configurations for Kafka, Connect and Zookeeper](./docs/KEY-CONFIGS.md)
5. [Monitoring and Alerting](./docs/MONITORING-ALERTING.md)
6. [How Kafka Works](./docs/HOW-KAFKA-WORKS.md)
7. [Design Patterns](./docs/DESIGN-PATTERNS.md)

## What is happening here?

![architecture-diagram](architecture.svg)

## Deployent Steps

### What are places that you should mention the Strimzi/Kafka versions?

1. [Kafka Connect Dockerfile](build-connect/Dockerfile)
2. [Helm chart parent values file](helm-charts/values.yaml)

### Todo

1. [Optional] build the kafka connect docker image. If you don't build - it will pull the mentioned Kafka Connect version.

   ```bash
   cd apps/kafka-plugins/connectors/weatherapi-connector
   make
   cd <build-connect>
   ```

   Now, Change the Dockerfile (base Kafka verison) and Makefile(DOCKER_TAG, DOCKER_VERSION_ARG) based on your need.

   ```bash
   make
   ```

2. [Optional] Build docker images for the producer, consumer and other utility applications
3. Prerequesties: Kubernetes Cluster / Kubectl
4. create a new namespace in the k8s

   ```bash
   kubectl create namespace kafka
   ```

5. install strimzi operator in the cluster/namespace

   ```bash
   kubectl -n kafka delete -f 'https://strimzi.io/install/latest?namespace=kafka'

   kubectl create -f 'https://strimzi.io/install/latest?namespace=kafka' -n kafka
   ```

6. install the prometheus operators. Download the latest operator from [here](https://raw.githubusercontent.com/prometheus-operator/prometheus-operator/master/bundle.yaml). Then change the `namespace` name from `default` to `kafka`. Then save the file to the CRD directory. Then run the following command to install the operator. Make sure to delete the previous operator before installing the new one.

   ```bash
   kubectl -n kafka delete -f 'https://raw.githubusercontent.com/prometheus-operator/prometheus-operator/master/bundle.yaml'

   kubectl create -f 'https://raw.githubusercontent.com/prometheus-operator/prometheus-operator/master/bundle.yaml' -n kafka

   ```

7. install ingress controller

   ```bash
   kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v1.3.0/deploy/static/provider/cloud/deploy.yaml
   ```

8. Add the following to the end of your `/etc/hosts` file

   ```bash
   sudo nano /etc/hosts
   ```

   ```txt
   127.0.0.1     strimzi.bridge.local
   ```

9. Deploy the helm chart

    ```bash
    cd helm-charts
    make
    ```

10. In case if you got lots of Evicted pods:

    ```bash
    kubectl get pod -n kafka | grep Evicted | awk '{print $1}' | xargs kubectl delete pod -n kafka
    ```

11. To setup the Grafana dashboard, follow the instructions [here](./grafana-dashboards/README.md).
12. When you are finished, you can uninstall the entire deployment with the following:

    ```bash
    helm uninstall demo-kafka -n kafka
    ```

### Interaction

kubectl -n kafka port-forward service/prometheus 31090:9090
kubectl -n kafka port-forward service/grafana 31999:3000
kubectl -n kafka port-forward service/kafka-connect-1-connect-api 38083:8083
kubectl -n kafka port-forward service/demo-kafka-kafdrop 30900:9000
