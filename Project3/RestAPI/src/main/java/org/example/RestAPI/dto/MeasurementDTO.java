package org.example.RestAPI.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.example.RestAPI.models.Sensor;

public class MeasurementDTO {
    @NotNull
    @Min(value = -100)
    @Max(value = 100)
    private float value;

    @NotNull
    private boolean raining;

    @NotNull
    private SensorDTO sensorDTO;


    public float getValue() {
        return value;
    }

    public void setValue( float value) {
        this.value = value;
    }


    public boolean getRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensorDTO;
    }

    public void setSensor(SensorDTO sensorDTO) {
        this.sensorDTO = sensorDTO;
    }
}
