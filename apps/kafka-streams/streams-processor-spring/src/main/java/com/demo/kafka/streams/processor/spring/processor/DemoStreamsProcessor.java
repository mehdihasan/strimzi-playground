package com.demo.kafka.streams.processor.spring.processor;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class DemoStreamsProcessor {

    @Value(value = "${input.topic.1}")
    private String inputTopic1;
    @Value(value = "${output.topic.1}")
    private String outputTopic1;
    @Value(value = "${output.topic.2}")
    private String outputTopic2;
    @Value(value = "${output.topic.3}")
    private String outputTopic3;

    private static final Serde<String> STRING_SERDE = Serdes.String();

    @Autowired
    void buildPipeline(StreamsBuilder streamsBuilder) {
        KStream<String, String> messageStream = streamsBuilder
                .stream(inputTopic1, Consumed.with(STRING_SERDE, STRING_SERDE));

        KStream<String, String> weatherLundStreams = messageStream
                .filter((key, value) -> "2693678".equals(key));

        KStream<String, String> weatherOsloStreams = messageStream
                .filter((key, value) -> "3143244".equals(key));

        KStream<String, String> weatherStockholmStreams = messageStream
                .filter((key, value) -> "2673730".equals(key));


        weatherLundStreams.to(outputTopic1);
        weatherOsloStreams.to(outputTopic2);
        weatherStockholmStreams.to(outputTopic3);
    }

}