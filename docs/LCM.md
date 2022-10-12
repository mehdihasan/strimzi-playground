# LCM

## Upgrading from AMQ Streams 1.8 directly to AMQ Streams 2.1

### Required upgrade sequence
- Make sure your Kubernetes cluster version is supported. AMQ Streams 2.1 is supported by OpenShift 4.6 to 4.10.
- Update your Cluster Operator to a new AMQ Streams version.
- Upgrade all Kafka brokers and client applications to the latest supported Kafka version.



## New Features

### Kafka 3.1.0
1. [Kafka 3.1.0 release notes](https://archive.apache.org/dist/kafka/3.1.0/RELEASE_NOTES.html)

### Strimzi 0.28.x / AMQ Streams 2.1
#### Kafka
1. [Loadbalancer Listener bootstrap service](https://access.redhat.com/documentation/en-us/red_hat_amq_streams/2.1/html/release_notes_for_amq_streams_2.1_on_openshift/enhancements-str#loadbalancer_listener_bootstrap_service)
2. [Setting limits on brokers using the Kafka Static Quota plugin](https://access.redhat.com/documentation/en-us/red_hat_amq_streams/2.1/html-single/configuring_amq_streams_on_openshift/index#proc-setting-broker-limits-str)
3. [Cruise Control for cluster rebalancing](https://access.redhat.com/documentation/en-us/red_hat_amq_streams/2.1/html-single/configuring_amq_streams_on_openshift/index#cruise-control-concepts-str)
#### Strimzi Bridge
1. [Using the Kafka Bridge with 3scale](https://access.redhat.com/documentation/en-us/red_hat_amq_streams/2.1/html-single/configuring_amq_streams_on_openshift/index#kafka-bridge-3-scale-str)



## [Resourse (CPU/Memory) Requirement](https://access.redhat.com/documentation/en-us/red_hat_amq_streams/2.1/html-single/configuring_amq_streams_on_openshift/index#con-common-configuration-resources-reference)
For Kafka, the following aspects of a deployment can impact the resources you need:
- Throughput and size of messages
- The number of network threads handling messages
- The number of producers and consumers
- The number of topics and partitions