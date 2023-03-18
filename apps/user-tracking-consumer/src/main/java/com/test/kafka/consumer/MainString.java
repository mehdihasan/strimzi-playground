package com.test.kafka.consumer;


import com.google.gson.Gson;
import com.test.kafka.model.Product;
import com.test.kafka.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

@Slf4j
public class MainString {

    private static Logger logger = LoggerFactory.getLogger(MainString.class);

    public static void main(String[] args) {

        Gson gsonObj = new Gson();

        SuggestionEngine suggestionEngine = new SuggestionEngine();

        Properties props = new Properties();
        // props.put("bootstrap.servers", "localhost:9092,localhost:9093");
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "user-tracking-consumer-v6");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {

            consumer.subscribe(List.of("user-prefer-product-in-string"));

            logger.info("User: --------------------------- >>>>>>>>>>>>> just test");

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

                for (ConsumerRecord<String, String> record : records) {

                    logger.info("User: {}", record.key());
                    logger.info("Product: {}", record.value());

                    User user = gsonObj.fromJson(record.key(), User.class);
                    Product product = gsonObj.fromJson(record.value(), Product.class);

                    logger.info("User: {}", user.toString());
                    logger.info("Product: {}", product.toString());

                    suggestionEngine.processSuggestions(user, product);
                }
            }
        }
    }
}
