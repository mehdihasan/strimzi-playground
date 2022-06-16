package com.custom.kafka.connect;

import com.custom.kafka.connect.model.Weather;
import com.google.gson.Gson;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static com.custom.kafka.connect.schema.WeatherAPISchemaFields.*;
import static com.custom.kafka.connect.schema.WeatherAPISchemas.*;
import static java.util.stream.Collectors.toList;

public class WeatherAPITask extends SourceTask {

    private WeatherAPIConfig config;
    private WeatherAPIClient client;
    private Gson gson;

    private AtomicBoolean isRunning = new AtomicBoolean(false);

    @Override
    public void start(Map<String, String> props) {
        Objects.requireNonNull(props);
        config = new WeatherAPIConfig(props);
        client = new WeatherAPIClient(config);
        gson = new Gson();
        isRunning.set(true);
    }

    @Override
    public List<SourceRecord> poll() throws InterruptedException {
        if (!isRunning.get()) {
            return Collections.emptyList();
        }

        Thread.sleep(config.getPollFrequency());

        if (config.isStruct()) {
            return client.getCurrentWeather().stream()
                    .map(weather -> new SourceRecord(sourcePartition(weather)
                            , sourceOffset()
                            , config.getKafkaTopic()
                            , KEY_SCHEMA
                            , buildKey(weather.getId())
                            , VALUE_SCHEMA, buildValue(weather)))
                    .collect(Collectors.toList());
        } else {
            return client.getCurrentWeather().stream()
                    .map(weather -> new SourceRecord(sourcePartition(weather)
                            , sourceOffset()
                            , config.getKafkaTopic()
                            , null
                            , weather.getId()
                            , null
                            , gson.toJson(weather)))
                    .collect(Collectors.toList());
        }
    }

    private Map<String, ?> sourcePartition(Weather weather) {
        Map<String, String> sourcePartition = new HashMap<>();
        sourcePartition.put("location", weather.getName());
        return sourcePartition;
    }

    private Map<String, ?> sourceOffset() {
        return new HashMap<>();
    }

    private Struct buildKey(Long id) {
        return new Struct(KEY_SCHEMA).put(ID, id);
    }

    private Struct buildValue(Weather weather) {
        return new Struct(VALUE_SCHEMA).put(NAME, weather.getName())
                .put(MAIN, new Struct(MAIN_SCHEMA).put(TEMP, weather.getMain().getTemp())
                        .put(PRESSURE, weather.getMain().getPressure())
                        .put(HUMIDITY, weather.getMain().getHumidity())
                        .put(TEMP_MIN, weather.getMain().getTempMin())
                        .put(TEMP_MAX, weather.getMain().getTempMax()))
                .put(WIND,
                        new Struct(WIND_SCHEMA).put(SPEED, weather.getWind().getSpeed()).put(DEG,
                                weather.getWind().getDeg()))
                .put(WEATHER, weather.getWeather().stream()
                        .map(weatherDetails -> new Struct(WEATHER_SCHEMA)
                                .put(ID, weatherDetails.getId())
                                .put(MAIN, weatherDetails.getMain())
                                .put(DESCRIPTION, weatherDetails.getDescription())
                                .put(ICON, weatherDetails.getIcon()))
                        .collect(toList()));
    }

    @Override
    public void stop() {
        isRunning.set(false);
    }

    @Override
    public String version() {
        return getClass().getPackage().getImplementationVersion();
    }
}
