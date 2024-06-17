package com.example.mqtt.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ObjectTransfromDto {
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Long id;
    private Long index;
    private Long stationId;
    private float positionX;
    private float positionY;
    private float positionZ;
    private float rotationX;
    private float rotationY;
    private float rotationZ;
    private float rotationW;
    private float scaleX;
    private float scaleY;
    private float scaleZ;
    private String videoUrl;
    private String maintenanceInstruction;
    private List<ObjectCommentDto> comments;
    private SensorDeviceDto sensorDevice;
}
