package com.test.kafka.producer;

import com.test.kafka.model.*;
import com.test.kafka.producer.model.Event;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.Properties;

import static java.lang.Thread.sleep;

@Slf4j
public class MainString {

    public static void main(String[] args) throws InterruptedException {

        EventGenerator eventGenerator = new EventGenerator();

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");

        // String SERIALIZER
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<>(props);

        for (int i = 1; i <= 10; i++) {
            log.info("Generating event #" + i);

            Event event = eventGenerator.generateEvent();
            User key = extractUserKey(event);
            Product value = extractProductValue(event);
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>("user-prefer-product-in-string", key.toString(), value.toString());

            log.info("Producing to Kafka the record: " + key + ":" + value);
            producer.send(producerRecord);

            sleep(1000);
        }

        producer.close();
    }

    private static User extractUserKey(Event event) {
        return User.newBuilder()
                .setUserId(event.getInternalUser().getUserId().toString())
                .setUsername(event.getInternalUser().getUsername())
                .setDateOfBirth((int)event.getInternalUser().getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).getLong(ChronoField.EPOCH_DAY))
                .build();
    }

    private static Product extractProductValue(Event event) {
        return Product.newBuilder()
                .setProductType(ProductType.valueOf(event.getInternalProduct().getType().name()))
                .setColor(Color.valueOf(event.getInternalProduct().getColor().name()))
                .setDesignType(DesignType.valueOf(event.getInternalProduct().getDesignType().name()))
                .build();
    }

}
