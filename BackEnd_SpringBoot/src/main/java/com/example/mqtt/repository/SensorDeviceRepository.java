package com.example.mqtt.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mqtt.entity.SensorDeviceEntity;
import com.example.mqtt.entity.StationEntity;

import java.util.List;



public interface SensorDeviceRepository extends JpaRepository<SensorDeviceEntity, Long>{
    List<SensorDeviceEntity> findByStation(StationEntity station);
}   
