package com.example.mqtt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mqtt.dto.ObjectTransfromDto;
import com.example.mqtt.dto.SensorDeviceDto;
import com.example.mqtt.repository.SensorDeviceRepository;
import com.example.mqtt.service.ObjectTransformService;
import com.example.mqtt.service.SensorDeviceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/sensor-device")
public class SensorDeviceController {
    private SensorDeviceService sensorDeviceService;


    @Autowired
    public SensorDeviceController(SensorDeviceService sensorDeviceService) {
        this.sensorDeviceService = sensorDeviceService;
    }

    @GetMapping(value = "/{stationId}")
    public ResponseEntity<List<SensorDeviceDto>> getTransform(@PathVariable("stationId") Long stationId) {
        try {
            List<SensorDeviceDto> sensorDeviceDtos = sensorDeviceService.getAllSensorDeviceByStationId(stationId);
            return ResponseEntity.status(HttpStatus.OK).body(sensorDeviceDtos);
        } catch (Exception e) {
            // Xử lý lỗi và trả về ResponseEntity lỗi với mã HTTP 400 Bad Request
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    
}
