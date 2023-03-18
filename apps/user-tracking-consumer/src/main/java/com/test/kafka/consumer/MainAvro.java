package com.test.kafka.consumer;


import com.test.kafka.model.Product;
import com.test.kafka.model.User;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

@Slf4j
public class MainAvro {

    public static void main(String[] args) {

        SuggestionEngine suggestionEngine = new SuggestionEngine();

        Properties props = new Properties();
        // props.put("bootstrap.servers", "localhost:9092,localhost:9093");
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "user-tracking-consumer-v3");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
        props.put("specific.avro.reader", "true");
        props.put("schema.registry.url", "http://localhost:8081");

        try (KafkaConsumer<User, Product> consumer = new KafkaConsumer<>(props)) {

            consumer.subscribe(List.of("user-prefer-product-in-avro"));

            while (true) {
                ConsumerRecords<User, Product> records = consumer.poll(Duration.ofMillis(100));

                for (ConsumerRecord<User, Product> record : records) {
                    suggestionEngine.processSuggestions(record.key(), record.value());
                }
            }
        }
    }
}
