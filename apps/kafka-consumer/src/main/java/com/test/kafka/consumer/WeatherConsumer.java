package com.test.kafka.consumer;

import com.example.avro.weather.*;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;


public class WeatherConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(WeatherConsumer.class);
    private static final String WEATHER_TOPIC = "weather";

    public static void main(String[] args) throws IOException {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.0.112:9093");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "weather.consumer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());

        KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<>(props);

        Thread shutdownHook = new Thread(consumer::close);
        Runtime.getRuntime().addShutdownHook(shutdownHook);

        consumer.subscribe(Collections.singletonList(WEATHER_TOPIC));

        while(true) {
            ConsumerRecords<String, byte[]> records = consumer.poll(Duration.ofMillis(100));

            for(ConsumerRecord<String, byte[]> record : records) {
                Weather weather = decodeAvroWeather(record.value());

                LOG.info("Consumed message: \n" + record.key() + " : " + weather.toString());
            }
        }
    }

    public static Weather decodeAvroWeather(byte[] array) throws IOException {
        return Weather.fromByteBuffer(ByteBuffer.wrap(array));
    }
}
