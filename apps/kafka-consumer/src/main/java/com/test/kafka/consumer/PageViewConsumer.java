package com.test.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.kafka.consumer.pageview.PageView;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * Kafka does not push messages to the consumer, rather consumer periodically pull
 * messages from Kafka broker.
 */
public class PageViewConsumer {
    
    public static void main(String[] args) throws IOException {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        /*
        props.put("security.protocol", "SASL_SSL");
        props.put("sasl.jaas.config",
                "org.apache.kafka.common.security.plain.PlainLoginModule   required username=\"GSXOH3AQK3PYTUML\"  " +
                        " password=\"a35AnGs+6bgYJMHxqoIOPBMRx4TqlukNDNm9NtXqMSYcMEwCqVjeolwfvoO8SwlG\";");
        props.put("ssl.endpoint.identification.algorithm", "https");
        props.put("sasl.mechanism", "PLAIN");
        */
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-consumer-group");
        props.put("enable.auto.commit", "false");
        props.put("auto.offset.reset", "earliest");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props,
                new StringDeserializer(),
                new StringDeserializer());

        consumer.subscribe(Arrays.asList("page-visits"));

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                processRecords(records);

                consumer.commitAsync();
            }
        } catch (Exception e) {
            consumer.close();
        }
    }

    private static void processRecords(ConsumerRecords<String, String> records) throws IOException {
        for (ConsumerRecord<String, String> record : records) {

            System.out.printf("Partition = %s, offset = %d, key = %s\n",
                    record.partition(),
                    record.offset(),
                    record.key());
            PageView pageView = parse(record.value());
            System.out.println(pageView);
        }
    }

    private static PageView parse(String pageViewStr) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(pageViewStr, PageView.class);
    }
}
