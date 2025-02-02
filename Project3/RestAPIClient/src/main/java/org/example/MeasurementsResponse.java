package org.example;

public class MeasurementsResponse {
    private float value;
    private boolean raining;
    private SensorResponse sensor;

    public MeasurementsResponse() {}

    public MeasurementsResponse(float value, boolean raining, SensorResponse sensor) {
        this.value = value;
        this.raining = raining;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public SensorResponse getSensor() {
        return sensor;
    }

    public void setSensor(SensorResponse sensor) {
        this.sensor = sensor;
    }
}
