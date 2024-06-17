package com.example.mqtt.service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mqtt.dto.ObjectCommentDto;
import com.example.mqtt.dto.ObjectTransfromDto;
import com.example.mqtt.entity.ObjectCommentEntity;
import com.example.mqtt.entity.ObjectTransformEntity;
import com.example.mqtt.entity.SensorDeviceEntity;
import com.example.mqtt.entity.StationEntity;
import com.example.mqtt.exception.ResourceNotFoundException;
import com.example.mqtt.repository.ObjectCommentRepository;
import com.example.mqtt.repository.ObjectTransformRepository;
import com.example.mqtt.repository.SensorDeviceRepository;
import com.example.mqtt.repository.StationRepository;

@Service
public class ObjectTransformService {

    private ModelMapper modelMapper;
    private ObjectTransformRepository objectTransformRepository;
    private StationRepository stationRepository;
    private ObjectCommentRepository objectCommentRepository;
    private SensorDeviceRepository sensorDeviceRepository;

    @Autowired
    public ObjectTransformService(
            ModelMapper modelMapper, ObjectTransformRepository objectTransformRepository,
            StationRepository stationRepository, ObjectCommentRepository objectCommentRepository, SensorDeviceRepository sensorDeviceRepository) {
        this.modelMapper = modelMapper;
        this.objectTransformRepository = objectTransformRepository;
        this.stationRepository = stationRepository;
        this.objectCommentRepository = objectCommentRepository;
        this.sensorDeviceRepository = sensorDeviceRepository;
    }

    public void createObjectTransform(ObjectTransfromDto objectTransfromDto) {
        ObjectTransformEntity objectTransformEntity = modelMapper.map(objectTransfromDto, ObjectTransformEntity.class);
        // long stationId = objectTransfromDto.getStationId();
        Long stationId = 1L;
        Optional<StationEntity> station = stationRepository.findById(stationId);
        if (station.get() != null) {
            Optional<SensorDeviceEntity> sensorDevice = sensorDeviceRepository.findById(objectTransformEntity.getSensorDevice().getId());
            objectTransformEntity.setSensorDevice(sensorDevice.get());
            objectTransformEntity.setIndex(Long.valueOf(station.get().getObjectTransforms().size()));
            objectTransformEntity.setStation(station.get());
            objectTransformRepository.save(objectTransformEntity);
        }
    }


    public List<ObjectTransfromDto> getObjectTransform(Long StationId) {
        Optional<StationEntity> station = stationRepository.findById(StationId);
        if (station.isPresent()) {
            List<ObjectTransformEntity> transformEntities = objectTransformRepository.findByStationIdOrderByIndex(StationId);
            return modelMapper.map(transformEntities, new TypeToken<List<ObjectTransfromDto>>() {
            }.getType());
        }
        return null;
    }

    public void createObjectComment(ObjectCommentDto objectCommentDto) throws ParseException {
        Optional<ObjectTransformEntity> objectTransformEntity = objectTransformRepository
                .findById(objectCommentDto.getObjectTransformId());
        if (objectTransformEntity != null) {
            String createdDay = objectCommentDto.getCreatedDay();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(createdDay, formatter);
            ObjectCommentEntity objectCommentEntity = new ObjectCommentEntity();
            objectCommentEntity.setContent(objectCommentDto.getContent());
            objectCommentEntity.setCreatedDay(dateTime);
            objectCommentEntity.setObjectTransform(objectTransformEntity.get());
            objectCommentEntity.setUserId("1");
            objectCommentRepository.save(objectCommentEntity);
        }

    }


    public void updateObject(ObjectTransfromDto objectTransfromDto) throws ParseException {

        boolean exists = objectTransformRepository
                .existsById(objectTransfromDto.getId());
        if (!exists) {
            throw new ResourceNotFoundException("Object", "Id", "not foudn't exist");
        }

        ObjectTransformEntity objectTransformEntity = modelMapper.map(objectTransfromDto, ObjectTransformEntity.class);
        SensorDeviceEntity sensorDeviceEntity = modelMapper.map(objectTransfromDto.getSensorDevice(), SensorDeviceEntity.class);
        // long stationId = objectTransfromDto.getStationId();
        Long stationId = 1L;
        Optional<StationEntity> station = stationRepository.findById(stationId);
        if (station.get() != null) {
            // Optional<SensorDeviceEntity> sensorDevice = sensorDeviceRepository.findById(objectTransformEntity.getSensorDevice().getId());
            objectTransformEntity.setSensorDevice(sensorDeviceEntity);
            // objectTransformEntity.setIndex(Long.valueOf(station.get().getObjectTransforms().size()));
            objectTransformEntity.setStation(station.get());
            objectTransformRepository.save(objectTransformEntity);
        }
    }

}
