package org.example.RestAPI.services;

import org.example.RestAPI.dto.MeasurementDTO;
import org.example.RestAPI.models.Measurement;
import org.example.RestAPI.models.Sensor;
import org.example.RestAPI.repositories.MeasurementsRepository;
import org.example.RestAPI.util.SensorNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementsRepository measurementsRepository;
    private final ModelMapper modelMapper;
    private final SensorsService sensorsService;

    @Autowired
    public MeasurementService(MeasurementsRepository measurementsRepository, ModelMapper modelMapper, SensorsService sensorsService) {
        this.measurementsRepository = measurementsRepository;
        this.modelMapper = modelMapper;
        this.sensorsService = sensorsService;
    }
    public List<Measurement> findAll(){
        return measurementsRepository.findAll();
    }
    @Transactional
    public void save(Measurement measurement) {
        Sensor sensor = sensorsService.findByName(measurement.getSensor().getName())
                .orElseThrow(() -> new SensorNotFoundException("Sensor not found"));
        measurement.setSensor(sensor);
        enrichMeasurement(measurement);
        measurementsRepository.save(measurement);
    }
    public int findByRaining() {
        return measurementsRepository.findAllByRaining(true).size();
    }



    public void enrichMeasurement(Measurement measurement) {
        measurement.setTimestamp(LocalDateTime.now());
    }
    public Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }
    public MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }


}
