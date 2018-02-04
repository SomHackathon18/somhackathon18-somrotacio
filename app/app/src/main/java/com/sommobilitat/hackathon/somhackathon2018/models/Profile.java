package com.sommobilitat.hackathon.somhackathon2018.models;

import java.util.ArrayList;

public class Profile {
    private int userId;
    private boolean handicapped;
    private ArrayList<Vehicle> vehiclesArrayList;

    public Profile(int userId, boolean handicapped, ArrayList<Vehicle> vehiclesArrayList) {
        super();
        this.userId = userId;
        this.handicapped = handicapped;
        this.vehiclesArrayList = vehiclesArrayList;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ArrayList<Vehicle> getVehiclesArrayList() {
        return vehiclesArrayList;
    }

    public void setVehiclesArrayList(ArrayList<Vehicle> vehiclesArrayList) {
        this.vehiclesArrayList = vehiclesArrayList;
    }

    public Vehicle getVehicleFromMatricula(String matricula) {
        for (int i = 0; i < vehiclesArrayList.size(); ++i) {
            if (vehiclesArrayList.get(i).getMatricula().equals(matricula)) {
                return vehiclesArrayList.get(i);
            }
        }
        return null;
    }

    public boolean isHandicapped() {
        return handicapped;
    }

    public void setHandicapped(boolean handicapped) {
        this.handicapped = handicapped;
    }
}
