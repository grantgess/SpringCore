package org.example.RestAPI.controllers;

import jakarta.validation.Valid;
import org.example.RestAPI.dto.MeasurementDTO;
import org.example.RestAPI.services.MeasurementService;
import org.example.RestAPI.services.SensorsService;
import org.example.RestAPI.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private final MeasurementService measurementService;
    private final SensorsService sensorsService;

    @Autowired
    public MeasurementsController(MeasurementService measurementService, SensorsService sensorsService) {
        this.measurementService = measurementService;
        this.sensorsService = sensorsService;
    }
    @GetMapping
    public List<MeasurementDTO> showAllMeasurements() {
        return measurementService.findAll().stream().map(measurementService::convertToMeasurementDTO).toList();
    }
    @GetMapping("/rainyDays")
    public int showRainingMeasurements() {
        return measurementService.findByRaining();
    }
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> createMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder str = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                str.append(fieldError.getField()).append(" : ")
                        .append(fieldError.getDefaultMessage()).append(";");
            }
            throw new MeasurementNotCreatedException(str.toString());
        }
        measurementService.save(measurementService.convertToMeasurement(measurementDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleExceptions(MeasurementNotCreatedException e) {
        MeasurementErrorResponse errorResponse = new MeasurementErrorResponse(e.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleExceptions(SensorNotFoundException e) {
        MeasurementErrorResponse errorResponse = new MeasurementErrorResponse(e.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


}
