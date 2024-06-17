package com.example.mqtt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mqtt.entity.ObjectTransformEntity;



public interface ObjectTransformRepository extends JpaRepository<ObjectTransformEntity, Long> {
    List<ObjectTransformEntity> findByStationId(Long stationId);
    List<ObjectTransformEntity> findByStationIdOrderByIndex(Long stationId);
}

