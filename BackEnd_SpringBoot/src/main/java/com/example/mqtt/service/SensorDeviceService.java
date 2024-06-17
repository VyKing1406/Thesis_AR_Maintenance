package com.example.mqtt.service;
import com.example.mqtt.dto.ObjectTransfromDto;
import com.example.mqtt.dto.SensorDeviceDto;
import com.example.mqtt.entity.SensorDeviceEntity;
import com.example.mqtt.entity.StationEntity;
import com.example.mqtt.repository.SensorDeviceRepository;
import com.example.mqtt.repository.StationRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SensorDeviceService {

    
    private SensorDeviceRepository sensorDeviceRepository;
    private StationRepository stationRepository;
    private ModelMapper modelMapper;

    @Autowired
    public SensorDeviceService(SensorDeviceRepository sensorDeviceRepository, StationRepository stationRepository, ModelMapper modelMapper) {
        this.sensorDeviceRepository = sensorDeviceRepository;
        this.stationRepository = stationRepository;
        this.modelMapper = modelMapper;
    }

    public BufferedImage generateQRCodeImage(String barcodeText) throws Exception {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 200, 200);

        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgbValue = bitMatrix.get(x, y) ? 0x000000 : 0xFFFFFF;
                image.setRGB(x, y, rgbValue);
            }
        }

        return image;
    }


    public List<SensorDeviceDto> getAllSensorDeviceByStationId(Long stationId) {
        Optional<StationEntity> stationEntity = stationRepository.findById(stationId);
        List<SensorDeviceEntity> sensorDevices = sensorDeviceRepository.findByStation(stationEntity.get());
        return modelMapper.map(sensorDevices, new TypeToken<List<SensorDeviceDto>>() {
            }.getType());
    }
}