all: deploy_chart

deploy_chart:
	helm template demo-kafka . -n kafka
	helm upgrade --install -f values.yaml demo-kafka . -n kafka