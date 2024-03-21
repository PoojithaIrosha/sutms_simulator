package com.poojithairosha.sutmssimulator.device;

import com.poojithairosha.sutmssimulator.util.RandomUtil;

import java.io.Serializable;

public class IoTDevice implements Serializable {
    private int vehicleSpeed;
    private TrafficLightStatus trafficLightStatus;
    private GPSCoordinates gpsCoordinates;

    public IoTDevice(TrafficLightLocation location) {
        this.gpsCoordinates = new GPSCoordinates(location);
    }

    public void captureData(TrafficLightStatus trafficLightStatus) {
        this.trafficLightStatus = trafficLightStatus;
        switch (trafficLightStatus) {
            case RED:
                this.vehicleSpeed = 0;
                break;
            case YELLOW:
                this.vehicleSpeed = RandomUtil.generateRandomNumber(10, 30);
                break;
            case FLASHING_YELLOW:
                this.vehicleSpeed = RandomUtil.generateRandomNumber(40, 60);
                break;
            case GREEN:
                this.vehicleSpeed = RandomUtil.generateRandomNumber(35, 60);
                break;
        }
    }

    public int getVehicleSpeed() {
        return vehicleSpeed;
    }

    public void setVehicleSpeed(int vehicleSpeed) {
        this.vehicleSpeed = vehicleSpeed;
    }

    public TrafficLightStatus getTrafficLightStatus() {
        return trafficLightStatus;
    }

    public void setTrafficLightStatus(TrafficLightStatus trafficLightStatus) {
        this.trafficLightStatus = trafficLightStatus;
    }

    public GPSCoordinates getGpsCoordinates() {
        return gpsCoordinates;
    }

    public void setGpsCoordinates(GPSCoordinates gpsCoordinates) {
        this.gpsCoordinates = gpsCoordinates;
    }

    @Override
    public String toString() {
        return "IoTDevice{" +
                "vehicleSpeed=" + vehicleSpeed +
                ", trafficLightStatus=" + trafficLightStatus +
                ", gpsCoordinates=" + gpsCoordinates +
                '}';
    }
}
