package com.poojithairosha.sutmssimulator.service;

import com.poojithairosha.sutmssimulator.device.IoTDevice;
import com.poojithairosha.sutmssimulator.device.TrafficLightStatus;
import jakarta.jms.JMSException;

import java.util.HashMap;
import java.util.Map;

public class SingleColorTrafficLightService extends Thread {
    private final IoTDevice ioTDevice;
    private final TrafficLightStatus lightStatus;

    public SingleColorTrafficLightService(IoTDevice ioTDevice, ThreadGroup threadGroup, TrafficLightStatus lightStatus) {
        super(threadGroup, ioTDevice.getGpsCoordinates().getLocation().toString());
        this.ioTDevice = ioTDevice;
        this.lightStatus = lightStatus;
    }

    @Override
    public void run() {
        while(true) {
            try {
                ioTDevice.captureData(lightStatus);
                transmit(ioTDevice);
                Thread.sleep(3000);
            } catch (JMSException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void transmit(IoTDevice ioTDevice) throws JMSException {
        DataTransmissionService transmitter = DataTransmissionService.getInstance("UTMSConnectionFactory", "UTMSQueue");
        HashMap<String, Object> map = new HashMap<>();
        map.put("vehicleSpeed", ioTDevice.getVehicleSpeed());
        map.put("trafficLightStatus", ioTDevice.getTrafficLightStatus().name());
        map.put("gpsCoordinates", Map.of("location", ioTDevice.getGpsCoordinates().getLocation().name(), "latitude", ioTDevice.getGpsCoordinates().getLatitude(), "longitude", ioTDevice.getGpsCoordinates().getLongitude()));
        map.put("time", System.currentTimeMillis());

        transmitter.transmit(map);
    }
}
