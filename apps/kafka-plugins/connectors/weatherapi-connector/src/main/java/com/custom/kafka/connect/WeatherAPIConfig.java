package com.custom.kafka.connect;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.util.HashMap;
import java.util.Map;

public class WeatherAPIConfig extends AbstractConfig {

    public static final String OPEN_WEATHER_API_KEY = "open.weather.api.key";
    private static final String OPEN_WEATHER_API_KEY_DOC = "8210a492417fb274440f643f9fb1ff6b";

    public static final String CITIES = "cities";
    private static final String CITIES_DOC = "Cities that you want to check weather for";

    public static final String POLL_FREQUENCY = "poll.frequency";
    private static final String POLL_FREQUENCY_DOC = "Duration between each request to Weather API";

    public static final String KAFKA_TOPIC = "kafka.topic";
    private static final String KAFKA_TOPIC_DOC = "Kafka Topic to send data to";

    private final String openWeatherApiKey;
    private final String cities;
    private final Long pollFrequency;
    private final String kafkaTopic;

    public WeatherAPIConfig(Map<String, ?> originals) {
        super(config(), originals);
        this.openWeatherApiKey = this.getString(OPEN_WEATHER_API_KEY);
        this.cities = this.getString(CITIES);
        this.pollFrequency = this.getLong(POLL_FREQUENCY);
        this.kafkaTopic = this.getString(KAFKA_TOPIC);
    }

    public static WeatherAPIConfig fromEnv() {
        String openWeatherApiKey = System.getenv(OPEN_WEATHER_API_KEY) == null
                ? "8210a492417fb274440f643f9fb1ff6b" : System.getenv(OPEN_WEATHER_API_KEY);
        String cities = System.getenv(CITIES) == null
                ? "Dhaka" : System.getenv(CITIES);
        String pollFrequency = System.getenv(POLL_FREQUENCY) == null
                ? "300000" : System.getenv(POLL_FREQUENCY);
        String kafkaTopic = System.getenv(KAFKA_TOPIC) == null
                ? "weather" : System.getenv(KAFKA_TOPIC);
        Map<String, String> originals = new HashMap<>();
        originals.put(OPEN_WEATHER_API_KEY, openWeatherApiKey);
        originals.put(CITIES, cities);
        originals.put(POLL_FREQUENCY, pollFrequency);
        originals.put(KAFKA_TOPIC, kafkaTopic);
        return new WeatherAPIConfig(originals);
    }

    public static ConfigDef config() {
        return new ConfigDef()
                .define(OPEN_WEATHER_API_KEY, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH,
                        OPEN_WEATHER_API_KEY_DOC)
                .define(CITIES, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, CITIES_DOC)
                .define(POLL_FREQUENCY, ConfigDef.Type.LONG, 100000L, ConfigDef.Importance.MEDIUM, POLL_FREQUENCY_DOC)
                .define(KAFKA_TOPIC, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, KAFKA_TOPIC_DOC);
    }

    public String getOpenWeatherApiKey() {
        return openWeatherApiKey;
    }

    public String getCities() {
        return cities;
    }

    public Long getPollFrequency() {
        return pollFrequency;
    }

    public String getKafkaTopic() {
        return kafkaTopic;
    }
}
