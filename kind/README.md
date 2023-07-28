# Kubernets environment

- [Kubernets environment](#kubernets-environment)
  - [Change Cluster context](#change-cluster-context)
  - [Create cluster](#create-cluster)
  - [Installing metric server](#installing-metric-server)
  - [Delete cluster](#delete-cluster)
  - [References](#references)

## Change Cluster context

If the cluster already exists then switch the context:

```bash
kubectl config use-context kind-kind
```

[Kubectl config set-context](https://www.airplane.dev/blog/kubectl-config-set-context-tutorial-and-best-practices)

## Create cluster

create a cluster with the config

```bash
kind create cluster --config kind-config.yaml
```

install ingress controller

```bash
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v1.3.0/deploy/static/provider/cloud/deploy.yaml
```

## Installing metric server

```bash
kubectl apply -f https://github.com/kubernetes-sigs/metrics-server/releases/download/v0.5.0/components.yaml
```

```bash
kubectl patch deployment metrics-server -n kube-system --patch "$(cat metric-server-patch.yaml)"
```

## Delete cluster

```bash
kind delete cluster
```

## References

1. [I have created a local Kubernetes cluster with kind. Following are changes you need to get metric-server running on Kind.](https://gist.github.com/sanketsudake/a089e691286bf2189bfedf295222bd43)
2. [How to connect to the Docker host from inside a Docker container?](https://medium.com/@TimvanBaarsen/how-to-connect-to-the-docker-host-from-inside-a-docker-container-112b4c71bc66)
3. [How to Test Ingress in a kind cluster](https://dustinspecker.com/posts/test-ingress-in-kind/)