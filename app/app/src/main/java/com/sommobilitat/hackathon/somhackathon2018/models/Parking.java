package com.sommobilitat.hackathon.somhackathon2018.models;

public class Parking {
    private Profile profileUser;
    private Vehicle vehicle;
    private int idParking;
    private int idServer;
    private String startTime;

    public Parking(Profile profile, Vehicle vehicle, int idParking, int idServer, String startTime) {
        super();

        this.profileUser = profile;
        this.vehicle = vehicle;
        this.idParking = idParking;
        this.idServer = idServer;
        this.startTime = startTime;
    }

    public Profile getProfileUser() {
        return profileUser;
    }

    public void setProfileUser(Profile profileUser) {
        this.profileUser = profileUser;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public int getIdParking() {
        return idParking;
    }

    public void setIdParking(int idParking) {
        this.idParking = idParking;
    }

    public int getIdServer() {
        return idServer;
    }

    public void setIdServer(int idServer) {
        this.idServer = idServer;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
