package com.example.mqtt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mqtt.dto.ObjectTransfromDto;
import com.example.mqtt.dto.SensorDto;
import com.example.mqtt.service.AiService;

@RestController
@RequestMapping("/api/")
public class AiController {
    private AiService aiService;

    @Autowired
    public AiController(AiService aiService) {
        this.aiService = aiService;
    }


    @PostMapping(value = "/sensors/{stationId}/predictions", consumes = "application/json")
    public ResponseEntity<String> createTransform(@PathVariable("stationId") String stationId, @RequestBody List<SensorDto> listSensorDto) {
        try {
            aiService.sendPredictData(stationId, listSensorDto);
            return ResponseEntity.status(HttpStatus.OK).body("Send sussces");
        } catch (Exception e) {
            // Xử lý lỗi và trả về ResponseEntity lỗi với mã HTTP 400 Bad Request
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }
}
