PROJECT_NAME=demo-kafka-connect
DOCKERFILE_DIR     ?= ./
DOCKER_REGISTRY    ?= docker.io
DOCKER_ORG         ?= mehdihasan
DOCKER_TAG         ?= 0.33.0-kafka-3.1.0
DOCKER_VERSION_ARG ?= 0.33.0-kafka-3.1.0

all: docker_build docker_push

docker_build:
	echo "Building Docker image ..."
	docker build --build-arg version=$(DOCKER_VERSION_ARG) -t $(DOCKER_ORG)/$(PROJECT_NAME):$(DOCKER_TAG) $(DOCKERFILE_DIR)

docker_tag:
	echo "Tagging $(DOCKER_ORG)/$(PROJECT_NAME):$(DOCKER_TAG) to $(DOCKER_REGISTRY)/$(DOCKER_ORG)/$(PROJECT_NAME):$(DOCKER_TAG) ..."
	docker tag $(DOCKER_ORG)/$(PROJECT_NAME):$(DOCKER_TAG) $(DOCKER_REGISTRY)/$(DOCKER_ORG)/$(PROJECT_NAME):$(DOCKER_TAG)

docker_push: docker_tag
	echo "Pushing $(DOCKER_REGISTRY)/$(DOCKER_ORG)/$(PROJECT_NAME):$(DOCKER_TAG) ..."
	docker push $(DOCKER_REGISTRY)/$(DOCKER_ORG)/$(PROJECT_NAME):$(DOCKER_TAG)
