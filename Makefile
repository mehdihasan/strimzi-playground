all: build_plugins build_kafka_connect_image

build_plugins:
	cd apps/kafka-plugins/connectors/weatherapi-connector && $(MAKE)

build_kafka_connect_image:
	cd build-connect && $(MAKE)

