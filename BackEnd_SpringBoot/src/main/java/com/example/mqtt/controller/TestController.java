package com.example.mqtt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mqtt.dto.SensorDto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.utilities.PushIdGenerator;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.MediaType;

@RestController
@RequestMapping("/device")
public class TestController {


    @GetMapping(value = "/AI/{station_id}")
    public CompletableFuture<Object> AI(@PathVariable("station_id") String station_id)
            throws Exception {
        System.out.println(station_id);        
        CompletableFuture<Object> future = new CompletableFuture<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("data_sensor/" + station_id);
        Query query = ref.orderByChild("created_at");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Object> sensorList = new ArrayList<>();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    SensorDto sensor = childSnapshot.getValue(SensorDto.class);
                    sensor.setCreated_at(sensor.getCreated_at());
                    sensorList.add(sensor);
                }

                future.complete(dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(databaseError.toException());
            }
        });
        return future;
    }



    @GetMapping(value = "/Identity")
    public String getId()
            throws Exception {
        // Tạo một Firebase Push ID
        long now = System.currentTimeMillis();

        // Tạo một Firebase Push ID từ thời gian hiện tại
        String pushId = PushIdGenerator.generatePushChildName(now);
        return pushId;
    }


    @PostMapping(value = "/mantainence", consumes = "application/json")
    public void getPosition(@RequestBody Object requestData)
            throws Exception {
        // Tạo một Firebase Push ID
        
        System.out.println(requestData);
    }
    // ...
}