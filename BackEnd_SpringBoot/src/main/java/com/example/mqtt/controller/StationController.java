package com.example.mqtt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mqtt.dto.StationDto;
import com.example.mqtt.dto.StationInforDto;
import com.example.mqtt.service.StationService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/station")
public class StationController {
    
    @Autowired
    private StationService serviceStation;

    @GetMapping("/{stationId}")
    public ResponseEntity<List<StationInforDto>> getMethodName(@PathVariable("stationId") String stationId) {
        return ResponseEntity.status(HttpStatus.OK).body(serviceStation.getStationInfo(stationId));
    }
    
}
