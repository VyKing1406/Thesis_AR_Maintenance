package com.example.mqtt.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.mqtt.dto.SensorDto;
import com.example.mqtt.dto.StationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class MqttService implements MqttCallback {

    private WebSocketService webSocketService;
    private MqttClient mqttClient;
    private SensorService sensorService;
    private ObjectMapper objectMapper;
    private Gson gson;

    @Value("${mqttBroker.username}")
    private String username;

    @Value("${mqttBroker.password}")
    private String password;

    @Autowired
    public MqttService(WebSocketService webSocketService, MqttClient mqttClient, SensorService sensorService,
            ObjectMapper objectMapper) {
        this.webSocketService = webSocketService;
        this.mqttClient = mqttClient;
        this.sensorService = sensorService;
        this.objectMapper = objectMapper;
        this.gson = new Gson();
    }

    public void connect() throws MqttException {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        mqttClient.setCallback(this);
        mqttClient.connect(options);
        mqttClient.subscribe("/innovation/airmonitoring/NBIOTs", 0);
    }

    @Override
    public void messageArrived(String UriTopic, MqttMessage message) throws Exception {
        try {
            String payload = message.toString();
            StationDto stationData = objectMapper.readValue(payload, StationDto.class);
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);
            for (SensorDto sensorData : stationData.getSensors()) {
                sensorData.setCreated_at(formattedDateTime);
                CompletableFuture<Void> saveDataFuture = CompletableFuture.runAsync(() -> {
                    try {
                        sensorService.saveSensorData(stationData.getStation_id(), sensorData);
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                saveDataFuture.join();
                webSocketService.sendMessageToAllClient(stationData.getStation_id(), gson.toJson(sensorData));
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void connectionLost(Throwable arg0) {
        throw new UnsupportedOperationException("Unimplemented method 'connectionLost'");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken arg0) {
        throw new UnsupportedOperationException("Unimplemented method 'deliveryComplete'");
    }
}