package com.example.mqtt.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mqtt.dto.SensorDeviceDto;
import com.example.mqtt.dto.StationInforDto;
import com.example.mqtt.repository.StationRepository;

@Service
public class StationService {
    
    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<StationInforDto> getStationInfo(String station_id) {
        List<StationInforDto> stationInforDtos = modelMapper.map(stationRepository.findByStationId(station_id), new TypeToken<List<StationInforDto>>() {
            }.getType());
        return stationInforDtos;
    }
}
