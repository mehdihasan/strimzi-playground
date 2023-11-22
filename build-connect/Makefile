PROJECT_NAME=demo-kafka-connect
DOCKERFILE_DIR     ?= ./
DOCKER_REGISTRY    ?= docker.io
DOCKER_ORG         ?= mehdihasan
DOCKER_TAG         ?= 0.38.0-strimzi-0.38.0-kafka-3.6.0
DOCKER_VERSION_ARG ?= 0.38.0-strimzi-0.38.0-kafka-3.6.0

all: make_plugin_dir build_connectors docker_build docker_tag docker_push delete_plugin_dir

make_plugin_dir:
	rm -rf my-plugins
	mkdir my-plugins

build_connectors:
	echo "building new weatherapi-connector"
	mvn -f ../apps/kafka-plugins/connectors/weatherapi-connector/pom.xml clean package
	echo "place the weatherapi-connector in the connect plugin folder"
	cp ../apps/kafka-plugins/connectors/weatherapi-connector/target/*-jar-with-dependencies.jar my-plugins/
	echo "done with connector build"

docker_build:
	echo "Building Docker image ..."
	docker build --build-arg version=$(DOCKER_VERSION_ARG) -t $(DOCKER_ORG)/$(PROJECT_NAME):$(DOCKER_TAG) $(DOCKERFILE_DIR)

docker_tag:
	echo "Tagging $(DOCKER_ORG)/$(PROJECT_NAME):$(DOCKER_TAG) to $(DOCKER_REGISTRY)/$(DOCKER_ORG)/$(PROJECT_NAME):$(DOCKER_TAG) ..."
	docker tag $(DOCKER_ORG)/$(PROJECT_NAME):$(DOCKER_TAG) $(DOCKER_REGISTRY)/$(DOCKER_ORG)/$(PROJECT_NAME):$(DOCKER_TAG)

docker_push:
	echo "Pushing $(DOCKER_REGISTRY)/$(DOCKER_ORG)/$(PROJECT_NAME):$(DOCKER_TAG) ..."
	docker push $(DOCKER_REGISTRY)/$(DOCKER_ORG)/$(PROJECT_NAME):$(DOCKER_TAG) || true

delete_plugin_dir:
	rm -rf my-plugins
