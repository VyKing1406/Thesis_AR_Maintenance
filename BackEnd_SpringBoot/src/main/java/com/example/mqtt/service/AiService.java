package com.example.mqtt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mqtt.dto.SensorDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AiService {
    private WebSocketService webSocketService;
    private ObjectMapper objectMapper;

    @Autowired
    private AiService(WebSocketService webSocketService, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.webSocketService = webSocketService;
    }

    public void sendPredictData(String stationId, List<SensorDto> listSensorDto) throws JsonProcessingException {
        for(SensorDto sensorDto : listSensorDto) {
            webSocketService.sendMessageToAllClient(stationId, objectMapper.writeValueAsString(sensorDto));
        }
    }   
}
