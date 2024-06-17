package com.example.mqtt.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "object_transfrom")
public class ObjectTransformEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "index", nullable = false)
    private Long index;

    @Column(name = "position_x", nullable = false)
    private float positionX;

    @Column(name = "position_y", nullable = false)
    private float positionY;

    @Column(name = "position_z", nullable = false)
    private float positionZ;

    @Column(name = "rotation_x", nullable = false)
    private float rotationX;

    @Column(name = "rotation_y", nullable = false)
    private float rotationY;

    @Column(name = "rotation_z", nullable = false)
    private float rotationZ;

    @Column(name = "rotation_w", nullable = false)
    private float rotationW;

    @Column(name = "scale_x", nullable = false)
    private float scaleX;

    @Column(name = "scale_y", nullable = false)
    private float scaleY;

    @Column(name = "scale_z", nullable = false)
    private float scaleZ;

    @Column(name = "videoUrl")
    private String videoUrl;

    @Column(name = "maintenance_instruction", columnDefinition = "TEXT")
    private String maintenanceInstruction;


    @OneToMany(mappedBy = "objectTransform", cascade = CascadeType.ALL)
    private List<ObjectCommentEntity> comments;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private StationEntity station;

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private SensorDeviceEntity sensorDevice;

}
