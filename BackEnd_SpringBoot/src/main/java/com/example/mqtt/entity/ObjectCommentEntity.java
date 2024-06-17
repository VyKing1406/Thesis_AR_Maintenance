package com.example.mqtt.entity;

import java.time.LocalDateTime;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "object_comment")
public class ObjectCommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "content", columnDefinition="TEXT") 
    private String content;

    @Column(name = "created_day")
    private LocalDateTime createdDay;

    @Column(name = "user_id")
    private String userId;

    @ManyToOne
    @JoinColumn(name = "object_transform_id")
    private ObjectTransformEntity objectTransform;
}
