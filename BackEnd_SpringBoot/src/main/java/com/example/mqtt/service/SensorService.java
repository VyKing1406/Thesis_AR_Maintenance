package com.example.mqtt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.mqtt.dto.SensorDto;
import com.example.mqtt.entity.SensorDeviceEntity;
import com.example.mqtt.entity.StationEntity;
import com.example.mqtt.exception.ResourceNotFoundException;
import com.example.mqtt.repository.SensorDeviceRepository;
import com.example.mqtt.repository.StationRepository;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

@Service
public class SensorService {

    private StationRepository stationRepository;
    public final FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Autowired
    SensorService(StationRepository stationRepository, SensorDeviceRepository sensorDeviceRepository) {
        this.stationRepository = stationRepository;
    }

    public void saveSensorData(String stationId, SensorDto sensorDTO) throws InterruptedException, ExecutionException {
        DatabaseReference ref = database
                .getReference("data_sensor" + "/" + stationId + "/" + sensorDTO.getId());
        ref.push().setValueAsync(sensorDTO);
    }

    public CompletableFuture<Boolean> updateBatchData(Map<String, Object> batchUpdateData) {
        CompletableFuture<Boolean> resultFuture = new CompletableFuture<>();
    
        DatabaseReference reference = database.getReference();
        reference.updateChildren(batchUpdateData, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    // Xử lý khi có lỗi xảy ra trong quá trình lưu batch
                    resultFuture.complete(false); // Trả về kết quả false
                } else {
                    // Thực hiện các tác vụ khác sau khi lưu batch thành công
                    System.out.println("Lưu batch thành công!");
                    resultFuture.complete(true); // Trả về kết quả true
                }
            }
        });
    
        return resultFuture;
    }

    public CompletableFuture<SensorDto> getSensorDataLastest(String stationId, String sensorId)
            throws InterruptedException, ExecutionException {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("data_sensor" + "/" + stationId + "/" + sensorId);
        CompletableFuture<SensorDto> future = new CompletableFuture<>();
        ref.orderByChild("created_at").limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SensorDto lastestData = dataSnapshot.getChildren().iterator().next().getValue(SensorDto.class);
                future.complete(lastestData);
            }
            @Override
            public void onCancelled(DatabaseError arg0) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'onCancelled'");
            }
        });
        return future;
    }

    public ResponseEntity<CompletableFuture<List<SensorDto>>> getSensorDataByTime(String stationId, String sensorId,
            int num, String startDateTime, String endDateTime) {
        List<StationEntity> stationEntities = stationRepository.findByStationId(stationId);
        if (stationEntities.isEmpty()) {
            throw new ResourceNotFoundException("Station", "Station_id", stationId);
        }
        Boolean checkSensorExists = false;
        for (SensorDeviceEntity sensorEntity : stationEntities.get(0).getSensors()) {
            if (sensorEntity.getSensorId().equals(sensorId)) {
                checkSensorExists = true;
                break;
            }
        }
        if (!checkSensorExists) {
            throw new ResourceNotFoundException("Sensor", "Sensor id", sensorId);
        }
        final CompletableFuture<List<SensorDto>> future = new CompletableFuture<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("data_sensor/" + stationId + "/" + sensorId);

        Query query = ref.orderByChild("created_at").limitToLast(num).startAt(startDateTime).endAt(endDateTime);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<SensorDto> sensorList = new ArrayList<>();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    SensorDto sensor = childSnapshot.getValue(SensorDto.class);
                    sensor.setCreated_at(sensor.getCreated_at());
                    sensorList.add(sensor);
                }

                future.complete(sensorList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(databaseError.toException());
            }
        });

        return ResponseEntity.ok(future);
    }
}
