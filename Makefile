all: pre_req k8s_setup kafka_env_setup build_kafka_connect_image deploy_project

pre_req:
	docker --version
	kind --version

k8s_setup:
	kind create cluster --config ./kind/kind-config.yaml || true
	kubectl delete job ingress-nginx-admission-create -n ingress-nginx || true
	kubectl delete job ingress-nginx-admission-patch -n ingress-nginx || true
	kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/kind/deploy.yaml --validate=false
	kubectl wait --namespace ingress-nginx \
		--for=condition=ready pod \
		--selector=app.kubernetes.io/component=controller \
		--timeout=90s
	kubectl apply -f https://github.com/kubernetes-sigs/metrics-server/releases/download/v0.5.0/components.yaml
	kubectl patch deployment metrics-server -n kube-system --patch-file ./kind/metric-server-patch.yaml

kafka_env_setup:
	kubectl create namespace kafka || true
	kubectl -n kafka delete -f ./CRDs/strimzi-cluster-operator-0.42.0.yaml || true
# 	kubectl create -f 'https://strimzi.io/install/latest?namespace=kafka' -n kafka
	kubectl -n kafka create -f ./CRDs/strimzi-cluster-operator-0.42.0.yaml
	kubectl -n kafka delete -f ./CRDs/prometheus-operator-deployment-0.15.0.yaml || true
# 	kubectl create -f 'https://raw.githubusercontent.com/prometheus-operator/prometheus-operator/master/bundle.yaml'
	kubectl -n kafka create -f ./CRDs/prometheus-operator-deployment-0.15.0.yaml

build_kafka_connect_image:
	cd build-connect && $(MAKE)

deploy_project:
	cd helm-charts && $(MAKE)
