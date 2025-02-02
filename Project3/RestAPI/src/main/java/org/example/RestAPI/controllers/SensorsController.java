package org.example.RestAPI.controllers;

import jakarta.validation.Valid;
import org.example.RestAPI.dto.SensorDTO;
import org.example.RestAPI.models.Sensor;
import org.example.RestAPI.repositories.SensorsRepository;
import org.example.RestAPI.services.SensorsService;
import org.example.RestAPI.util.SensorErrorResponse;
import org.example.RestAPI.util.SensorNotCreatedException;
import org.example.RestAPI.util.SensorValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorsController {
    private final SensorsService sensorsService;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorsController(SensorsService sensorsService, SensorValidator sensorValidator) {
        this.sensorsService = sensorsService;
        this.sensorValidator = sensorValidator;
    }
    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> createSensor(@RequestBody @Valid SensorDTO sensorDTO,
                                                   BindingResult bindingResult) {
        sensorValidator.validate(sensorsService.convertToSensor(sensorDTO), bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder str = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                str.append(fieldError.getField()).append(" : ")
                        .append(fieldError.getDefaultMessage()).append(";");
            }
            throw new SensorNotCreatedException(str.toString());
        }
        sensorsService.save(sensorsService.convertToSensor(sensorDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleExceptions(SensorNotCreatedException e) {
        SensorErrorResponse errorResponse = new SensorErrorResponse(e.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }



}
