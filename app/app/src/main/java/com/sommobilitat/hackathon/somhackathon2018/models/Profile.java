package com.sommobilitat.hackathon.somhackathon2018.models;

import java.util.ArrayList;

public class Profile {
    private int userId;
    private ArrayList<Vehicle> vehiclesArrayList;

    public Profile(int userId, ArrayList<Vehicle> vehiclesArrayList) {
        super();
        this.userId = userId;
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
}
