package com.example.mqtt.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mqtt.entity.ObjectCommentEntity;


public interface ObjectCommentRepository extends JpaRepository<ObjectCommentEntity, Long>{
    
}
