package com.sommobilitat.hackathon.somhackathon2018.models;

import java.util.ArrayList;

public class Zones {
    private int id;
    private String name;
    private double area;
    private double perimeter;
    private double width;
    private double height;
    private int nPlacesTotals;
    private int nPlacesOcuppied;
    private LatLng center;
    private ArrayList<LatLng> wkt;

    public Zones(int id, String name, double area, double perimeter, double width, double height, int nPlacesTotals, int nPlacesOcuppied, LatLng center, ArrayList<LatLng> wkt) {
        this.id = id;
        this.name = name;
        this.area = area;
        this.perimeter = perimeter;
        this.width = width;
        this.height = height;
        this.nPlacesTotals = nPlacesTotals;
        this.nPlacesOcuppied = nPlacesOcuppied;
        this.center = center;
        this.wkt = wkt;
    }

    public static ArrayList<LatLng> polygonToWKTArray(String wktArray) {
        wktArray = wktArray.replace("POLYGON ((", "");
        wktArray = wktArray.replace("))", "");
        wktArray = wktArray.replace(", ", ",");

        String[] wktArraySplitted = wktArray.split(",");
        ArrayList<LatLng> latLngArrayList = new ArrayList<>();

        for (int i = 0; i < wktArraySplitted.length; ++i) {
            String[] wtkSingle = wktArraySplitted[i].split(" ");
            latLngArrayList.add(new LatLng(Double.parseDouble(wtkSingle[1]), Double.parseDouble(wtkSingle[0])));
        }

        return latLngArrayList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getPerimeter() {
        return perimeter;
    }

    public void setPerimeter(double perimeter) {
        this.perimeter = perimeter;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getnPlacesTotals() {
        return nPlacesTotals;
    }

    public void setnPlacesTotals(int nPlacesTotals) {
        this.nPlacesTotals = nPlacesTotals;
    }

    public int getnPlacesOcuppied() {
        return nPlacesOcuppied;
    }

    public void setnPlacesOcuppied(int nPlacesOcuppied) {
        this.nPlacesOcuppied = nPlacesOcuppied;
    }

    public LatLng getCenter() {
        return center;
    }

    public void setCenter(LatLng center) {
        this.center = center;
    }

    public ArrayList<LatLng> getWkt() {
        return wkt;
    }

    public void setWkt(ArrayList<LatLng> wkt) {
        this.wkt = wkt;
    }
}
