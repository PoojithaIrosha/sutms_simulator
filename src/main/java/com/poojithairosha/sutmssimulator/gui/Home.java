package com.poojithairosha.sutmssimulator.gui;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.poojithairosha.sutmssimulator.device.IoTDevice;
import com.poojithairosha.sutmssimulator.device.TrafficLightLocation;
import com.poojithairosha.sutmssimulator.device.TrafficLightStatus;
import com.poojithairosha.sutmssimulator.service.SingleColorTrafficLightService;
import com.poojithairosha.sutmssimulator.service.TrafficLightService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JFrame {
    private JPanel panel1;
    private JButton btnSimulate;
    private JButton btnRed;
    private JButton btnYellow;
    private JButton btnGreen;
    private JComboBox<String> locationsComboBox;
    private boolean isActive = false;
    private final ThreadGroup lightServiceGroup = new ThreadGroup("TrafficLightServiceGroup");

    public Home() throws HeadlessException {
        init();
    }

    public void init() {
        setTitle("UTMS Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(475, 350);
        setLocationRelativeTo(null);
        setContentPane(panel1);

        DefaultComboBoxModel<String> boxModel = (DefaultComboBoxModel<String>) locationsComboBox.getModel();
        boxModel.addElement("Select Location");
        for (TrafficLightLocation location : TrafficLightLocation.values()) {
            boxModel.addElement(location.name());
        }

        btnSimulate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // Start the simulation
                btnSimulate.setEnabled(false);
                if (isActive) {
                    stopSimulation();
                } else {
                    btnSimulate.setText("STOP SIMULATION");
                    new Thread(lightServiceGroup, () -> {
                        btnSimulate.setEnabled(true);
                        for (int i = 0; i < TrafficLightLocation.values().length; i++) {
                            new TrafficLightService(new IoTDevice(TrafficLightLocation.values()[i]), lightServiceGroup).start();
                            try {
                                Thread.sleep(10000);
                            } catch (InterruptedException e) {
                                break;
                            }
                        }
                    }).start();
                }
                isActive = !isActive;
            }
        });

        btnRed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!String.valueOf(locationsComboBox.getSelectedItem()).equals("Select Location")) {
                    TrafficLightLocation location = TrafficLightLocation.valueOf(String.valueOf(locationsComboBox.getSelectedItem()));
                    System.out.println(location);
                    startSingleColorTrafficLight(btnRed, location, TrafficLightStatus.RED);
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Please select a location", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnYellow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!String.valueOf(locationsComboBox.getSelectedItem()).equals("Select Location")) {
                    TrafficLightLocation location = TrafficLightLocation.valueOf(String.valueOf(locationsComboBox.getSelectedItem()));
                    System.out.println(location);
                    startSingleColorTrafficLight(btnYellow, location, TrafficLightStatus.YELLOW);
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Please select a location", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnGreen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!String.valueOf(locationsComboBox.getSelectedItem()).equals("Select Location")) {
                    TrafficLightLocation location = TrafficLightLocation.valueOf(String.valueOf(locationsComboBox.getSelectedItem()));
                    System.out.println(location);
                    startSingleColorTrafficLight(btnGreen, location, TrafficLightStatus.GREEN);
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Please select a location", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void startSingleColorTrafficLight(JButton button, TrafficLightLocation location, TrafficLightStatus status) {
        if (button.getText().equals("STOP")) {
            stopSingleThread(location.name());
            button.setText(status.name());
            if (isActive) {
                new TrafficLightService(new IoTDevice(location), lightServiceGroup).start();
            }
            return;
        } else {
            button.setText("STOP");
        }

        if (isActive) {
            stopSingleThread(location.name());
        }

        SingleColorTrafficLightService service = new SingleColorTrafficLightService(new IoTDevice(location), lightServiceGroup, status);
        service.start();
    }

    public void stopSingleThread(String name) {
        Thread[] threads = new Thread[lightServiceGroup.activeCount()];
        lightServiceGroup.enumerate(threads);

        for (Thread t : threads) {
            if (t.getName().equals(name)) {
                System.out.println("Interrupting " + t.getName());
                t.interrupt();
                break;
            }
        }
    }

    public void stopSimulation() {
        lightServiceGroup.interrupt();
        btnSimulate.setText("START SIMULATION");
        btnSimulate.setEnabled(true);
        btnRed.setText("RED");
        btnRed.setEnabled(true);
        btnYellow.setText("YELLOW");
        btnYellow.setEnabled(true);
        btnGreen.setText("GREEN");
        btnGreen.setEnabled(true);
    }

    public static void main(String[] args) {
        FlatMacDarkLaf.setup();
        new Home().setVisible(true);
    }

}
