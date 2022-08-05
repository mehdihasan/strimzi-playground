all: create_secrets deploy_chart

create_secrets:
	-kubectl -n kafka create secret generic basic-auth --from-file=auth

deploy_chart:
	helm template demo-kafka . -n kafka
	helm upgrade --install -f values.yaml demo-kafka . -n kafka