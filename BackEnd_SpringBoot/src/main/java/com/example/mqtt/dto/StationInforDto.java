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
public class StationInforDto {
    private long id;
    private String stationId;
    private String stationName;
    private Double gpsLongitude;
    private Double gpsLatitude;
}
