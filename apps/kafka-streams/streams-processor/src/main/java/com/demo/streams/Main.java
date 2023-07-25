package com.demo.streams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import static org.slf4j.LoggerFactory.getLogger;


public class Main {

    private static final Logger LOGGER = getLogger(Main.class);
    private static final String DEFAULT_INPUT_TOPIC = "weather-data-v0";
    private static final String DEFAULT_OUTPUT_TOPIC = "weather-data-lund-v0";
    private static final String DEFAULT_BOOTSTRAP_SERVERS = "localhost:9092";
    private static final Integer DEFAULT_MAX_REQUEST_SIZE = 25728640;

    private static final String BOOTSTRAP_SERVERS = "BOOTSTRAP_SERVERS";
    private static final String INPUT_TOPIC = "INPUT_TOPIC";
    private static final String OUTPUT_TOPIC = "OUTPUT_TOPIC";

    String bootstrapServers = System.getenv().getOrDefault(BOOTSTRAP_SERVERS, DEFAULT_BOOTSTRAP_SERVERS);
    String inputTopic = System.getenv().getOrDefault(INPUT_TOPIC, DEFAULT_INPUT_TOPIC);
    String outputTopic = System.getenv().getOrDefault(OUTPUT_TOPIC, DEFAULT_OUTPUT_TOPIC);


    public Properties createProperties() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "demo-streams-processor");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.ByteArray().getClass());
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put("max.request.size", DEFAULT_MAX_REQUEST_SIZE);
        return props;
    }


    public Topology createTopology() {
        final StreamsBuilder builder = new StreamsBuilder();
        KStream<String, byte[]> stream = builder.stream(inputTopic);

        stream.mapValues(recordAsByteArray -> {

            try {
                return process(recordAsByteArray);
            } catch (Exception e) {
                e.printStackTrace();
                return "null";
            }

        })
        .peek((k, res) -> System.out.println("PRINTING MESSAGE: " + k + " -> " + res))
        .to(outputTopic, Produced.with(
                Serdes.String(),
                Serdes.String()
        ));
        return builder.build();
    }


    private String process(byte[] payload) throws IOException {
        return new String(payload);
    }


    public static void main(String[] args) throws IOException {

        Main app = new Main();

        Properties props = app.createProperties();

        final Topology topology = app.createTopology();
        final KafkaStreams streams = new KafkaStreams(topology, props);
        final CountDownLatch latch = new CountDownLatch(1);

        Runtime.getRuntime().addShutdownHook(
                new Thread("kafka-streams-shutdown-hook") {
            @Override
            public void run() {
                streams.close();
                latch.countDown();
            }
        });

        try {
            streams.start();
            latch.await();
        } catch (Throwable e) {
            System.exit(1);
        }
        System.exit(0);
    }
}