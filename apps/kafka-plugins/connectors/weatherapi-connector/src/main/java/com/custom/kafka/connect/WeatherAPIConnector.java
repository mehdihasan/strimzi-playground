package com.custom.kafka.connect;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;
import org.apache.kafka.connect.util.ConnectorUtils;

import java.util.*;
import java.util.stream.Collectors;

public class WeatherAPIConnector extends SourceConnector {

    private WeatherAPIConfig config;

    @Override
    public void start(Map<String, String> props) {
        Objects.requireNonNull(props);
        config = new WeatherAPIConfig(props);
    }

    @Override
    public Class<? extends Task> taskClass() {
        return WeatherAPITask.class;
    }

    @Override
    public List<Map<String, String>> taskConfigs(int maxTasks) {
        List<String> cities = Arrays.stream(config.getCities().split(","))
                .collect(Collectors.toList());

        int numGroups = Math.min(cities.size(), maxTasks);

        return ConnectorUtils.groupPartitions(cities, numGroups)
                .stream()
                .map(taskCities -> {
                    Map<String, String> taskProps = new HashMap<>(config.originalsStrings());
                    taskProps.put(WeatherAPIConfig.CITIES, String.join(",", taskCities));
                    return taskProps;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void stop() {

    }

    @Override
    public ConfigDef config() {
        return WeatherAPIConfig.config();
    }

    @Override
    public String version() {
        return getClass().getPackage().getImplementationVersion();
    }
}
