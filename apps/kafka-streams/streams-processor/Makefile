PROJECT_NAME=streams-processor

DOCKERFILE_DIR     ?= ./
DOCKER_REGISTRY    ?= docker.io
DOCKER_ORG         ?= mehdihasan
DOCKER_TAG         ?= 0.3
DOCKER_VERSION_ARG ?= 0.3

all: docker_build docker_push

docker_build:
	echo "Building Docker image ..."
	docker build --build-arg version=$(DOCKER_VERSION_ARG) -t mehdihasan/$(PROJECT_NAME):$(DOCKER_TAG) $(DOCKERFILE_DIR)

docker_tag:
	echo "Tagging mehdihasan/$(PROJECT_NAME):$(DOCKER_TAG) to $(DOCKER_REGISTRY)/$(DOCKER_ORG)/$(PROJECT_NAME):$(DOCKER_TAG) ..."
	docker tag mehdihasan/$(PROJECT_NAME):$(DOCKER_TAG) $(DOCKER_REGISTRY)/$(DOCKER_ORG)/$(PROJECT_NAME):$(DOCKER_TAG)

docker_push: docker_tag
	echo "Pushing $(DOCKER_REGISTRY)/$(DOCKER_ORG)/$(PROJECT_NAME):$(DOCKER_TAG) ..."
	docker push $(DOCKER_REGISTRY)/$(DOCKER_ORG)/$(PROJECT_NAME):$(DOCKER_TAG)
