PROJECT_NAME=demo-streams-processor-spring
DOCKERFILE_DIR     ?= ./
DOCKER_REGISTRY    ?= docker.io
DOCKER_ORG         ?= mehdihasan
DOCKER_TAG         ?= 0.0.2-SNAPSHOT
DOCKER_VERSION_ARG ?= 0.0.2-SNAPSHOT

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