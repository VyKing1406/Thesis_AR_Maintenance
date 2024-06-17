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
public class SensorDto {
    private String value;
    private String error_predict;
    private String type;
    private String id;
    private String created_at;
}
