package com.poojithairosha.sutmssimulator.service;

import com.poojithairosha.sutmssimulator.device.IoTDevice;
import com.poojithairosha.sutmssimulator.device.TrafficLightStatus;
import jakarta.jms.JMSException;

import java.util.HashMap;
import java.util.Map;

public class TrafficLightService extends Thread {

    private final IoTDevice ioTDevice;

    public TrafficLightService(IoTDevice ioTDevice, ThreadGroup threadGroup) {
        super(threadGroup, ioTDevice.getGpsCoordinates().getLocation().toString());
        this.ioTDevice = ioTDevice;
    }

    @Override
    public void run() {
        while (true) {

            Thread threadRed = new Thread(() -> {
                while (true) {
                    synchronized (ioTDevice) {
                        try {
                            ioTDevice.captureData(TrafficLightStatus.RED);
                            transmit(ioTDevice);
                            Thread.sleep(3000);
                        } catch (JMSException e) {
                            throw new RuntimeException(e);
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                }
            });

            Thread threadYellow = new Thread(() -> {
                while (true) {
                    synchronized (ioTDevice) {
                        try {
                            ioTDevice.captureData(TrafficLightStatus.YELLOW);
                            transmit(ioTDevice);
                            Thread.sleep(3000);
                        } catch (JMSException e) {
                            throw new RuntimeException(e);
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                }
            });

            Thread threadYellow2 = new Thread(() -> {
                while (true) {
                    synchronized (ioTDevice) {
                        try {
                            ioTDevice.captureData(TrafficLightStatus.YELLOW);
                            transmit(ioTDevice);
                            Thread.sleep(3000);
                        } catch (JMSException e) {
                            throw new RuntimeException(e);
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                }
            });

            Thread threadGreen = new Thread(() -> {
                while (true) {
                    synchronized (ioTDevice) {
                        try {
                            ioTDevice.captureData(TrafficLightStatus.GREEN);
                            transmit(ioTDevice);
                            Thread.sleep(3000);
                        } catch (JMSException e) {
                            throw new RuntimeException(e);
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                }
            });

            try {
                threadRed.start();
                Thread.sleep(1000 * 10);
                threadRed.interrupt();

                threadYellow.start();
                Thread.sleep(1000 * 4);
                threadYellow.interrupt();

                threadGreen.start();
                Thread.sleep(1000 * 10);
                threadGreen.interrupt();

                threadYellow2.start();
                Thread.sleep(1000 * 4);
                threadYellow2.interrupt();
            } catch (InterruptedException e) {
                threadRed.interrupt();
                threadYellow.interrupt();
                threadGreen.interrupt();
                threadYellow2.interrupt();
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
