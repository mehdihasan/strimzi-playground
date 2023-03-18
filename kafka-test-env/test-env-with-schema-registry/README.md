# Test environment with Schema Registry

## Run Kafka

```bash
./gradlew jar

./gradlew srcJar

KAFKA_CLUSTER_ID="$(./bin/kafka-storage.sh random-uuid)"
    ./bin/kafka-storage.sh format -t $KAFKA_CLUSTER_ID -c config/kraft/server.properties
    ./bin/kafka-server-start.sh config/kraft/server.properties
```

## Run Schema Registry

```bash
docker compose -f ./docker-compose-sr.yaml up -d

# To setup the database the first time you run the app, need to call rails db:setup from within the container

docker exec avro-schema-registry bundle exec rails db:setup
```

## Run Kafdrop

```bash
export SCHEMAREGISTRY_CONNECT=http://localhost:8081 && mvn spring-boot:run
```
