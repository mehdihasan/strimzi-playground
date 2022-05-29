# Deployment using Helm Charts


helm status demo-kafka -n kafka

helm template demo-kafka . -n kafka
helm upgrade --install -f values.yaml strimzi-kafka . -n kafka


helm uninstall demo-kafka -n kafka


## Experiments

[ref](https://github.com/obsidiandynamics/kafdrop)

git clone https://github.com/obsidiandynamics/kafdrop && cd kafdrop

helm upgrade -i kafdrop chart --set image.tag=3.30.0 --set kafka.brokerConnect=kafka-cluster-1-kafka-brokers:9090 -n kafka


helm uninstall kafdrop -n kafka