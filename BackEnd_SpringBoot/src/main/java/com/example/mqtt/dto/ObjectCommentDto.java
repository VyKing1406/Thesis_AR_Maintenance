package com.example.mqtt.dto;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ObjectCommentDto {
    private long id;
    private Long objectTransformId;
    private String content;
    private String createdDay;
    private String userId;
}
