package org.example.RestAPI.services;

import org.example.RestAPI.dto.SensorDTO;
import org.example.RestAPI.models.Sensor;
import org.example.RestAPI.repositories.SensorsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorsService {
    private final SensorsRepository sensorsRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorsService(SensorsRepository sensorsRepository, ModelMapper modelMapper) {
        this.sensorsRepository = sensorsRepository;
        this.modelMapper = modelMapper;
    }
    @Transactional
    public void save(Sensor sensor) {
        sensorsRepository.save(sensor);
    }
    public List<Sensor> findAll() {
        return sensorsRepository.findAll();
    }
    public Optional<Sensor> findByName(String name) {
        return  sensorsRepository.findByName(name);
    }

    public SensorDTO convertToSensorDTO(Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }
    public Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }
}
