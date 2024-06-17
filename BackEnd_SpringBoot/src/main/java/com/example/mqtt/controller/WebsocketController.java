package com.example.mqtt.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mqtt.dto.SensorDto;
import com.example.mqtt.service.SensorService;

@RestController()
@RequestMapping("/api")
public class WebsocketController {

    @Autowired
    private SensorService sensorService;


    WebsocketController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @GetMapping("/sensors/{stationId}/{sensorId}/{num}/{startDateTime}/{endDateTime}")
    public ResponseEntity<CompletableFuture<List<SensorDto>>> getSensorDataByTime(@PathVariable("stationId") String stationId,
            @PathVariable("sensorId") String sensorId, @PathVariable("num") String num, @PathVariable("startDateTime") String startDateTime,
            @PathVariable("endDateTime") String endDateTime) throws InterruptedException,
            ExecutionException {
        ResponseEntity<CompletableFuture<List<SensorDto>>> futureData = sensorService.getSensorDataByTime(stationId, sensorId, Integer.parseInt(num), startDateTime,
                endDateTime);
        return futureData;
    }

    @GetMapping("/sensors/{stationId}/{id}")
    public SensorDto test(@PathVariable("stationId") String station_id,
            @PathVariable("id") String id) throws InterruptedException,
            ExecutionException {
        CompletableFuture<SensorDto> latestedData = sensorService.getSensorDataLastest(station_id, id);
        return latestedData.get();
    }

}