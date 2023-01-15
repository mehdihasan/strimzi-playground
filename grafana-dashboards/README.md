# Grafana Setup

## Instruction for Kind Cluster

1. first, get the Grafana PORT adress using the following command:

   ```bash
   oc get svc grafana -n kafka
   ```

2. Expose the port for Grafana:

   ```bash
   kubectl port-forward service/grafana <PORT>:3000 -n kafka
   ```

## Access

<http://localhost:31587/?orgId=1>

admin/admin

## Setup

1. first, get the server IP and PORT adress using the following command:

   ```bash
   oc get svc prometheus -n kafka
   ```

2. set the [datasource](http://localhost:31761/datasources) as Prometheus. E.g. `http://10.96.228.120:9090` in the URL field and `Save & Test`.
3. Hover on the plus(+) icon from the left side menu and click on `import`.
4. Then import all the dashboards from the following directory [grafana-dashboards](./grafana-dashboards/).

## References

1. [Install Prometheus Operator with Grafana Cloud for Kubernetes](https://grafana.com/docs/grafana-cloud/kubernetes-monitoring/other-methods/prometheus/prometheus_operator/)
