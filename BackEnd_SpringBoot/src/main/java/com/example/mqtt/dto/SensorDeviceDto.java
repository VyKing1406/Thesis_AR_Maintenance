package com.example.mqtt.dto;

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
public class SensorDeviceDto {
    private Long id;
    private String sensorId;
    private String sensorname;
    private String sensorUnit;
    private Long stationId;
    private String sensorImageUrl;
}
