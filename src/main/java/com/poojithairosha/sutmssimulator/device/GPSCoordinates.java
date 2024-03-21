package com.poojithairosha.sutmssimulator.device;

import java.io.Serializable;
import java.util.HashMap;

public class GPSCoordinates implements Serializable {
    private static final HashMap<TrafficLightLocation, HashMap<String, Double>> sampleData = new HashMap<>();
    private double latitude;
    private double longitude;
    private TrafficLightLocation location;

    static {
        HashMap<String, Double> kandy = new HashMap<>();
        kandy.put("latitude", 7.2906);
        kandy.put("longitude", 80.6337);
        sampleData.put(TrafficLightLocation.KANDY, kandy);

        HashMap<String, Double> peradeniya = new HashMap<>();
        peradeniya.put("latitude", 7.2699);
        peradeniya.put("longitude", 80.5938);
        sampleData.put(TrafficLightLocation.PERADENIYA, peradeniya);

        HashMap<String, Double> pilimathalawa = new HashMap<>();
        pilimathalawa.put("latitude", 7.2663);
        pilimathalawa.put("longitude", 80.5522);
        sampleData.put(TrafficLightLocation.PILIMATHALAWA, pilimathalawa);

        HashMap<String, Double> katugastota = new HashMap<>();
        katugastota.put("latitude", 7.3360);
        katugastota.put("longitude", 80.6214);
        sampleData.put(TrafficLightLocation.KATUGASTOTA, katugastota);
    }

    public GPSCoordinates(TrafficLightLocation location) {
        HashMap<String, Double> stringDoubleHashMap = sampleData.get(location);
        this.latitude = stringDoubleHashMap.get("latitude");
        this.longitude = stringDoubleHashMap.get("longitude");
        this.location = location;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public TrafficLightLocation getLocation() {
        return location;
    }

    public void setLocation(TrafficLightLocation location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "GPSCoordinates{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", location=" + location +
                '}';
    }
}
