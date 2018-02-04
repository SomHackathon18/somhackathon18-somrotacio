package com.sommobilitat.hackathon.somhackathon2018.controllers;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.EditText;

import com.sommobilitat.hackathon.somhackathon2018.models.Profile;
import com.sommobilitat.hackathon.somhackathon2018.models.Vehicle;

import java.util.ArrayList;

public class ProfileController {
    public static final int INDEX_BASE_PROFILE = 1;
    public static final String KEY_USER_VEHICLES = "KEY_USER_VEHICLES";

    public static Profile getProfileFromDB(Context context, int userId) {
        boolean isHandicapped = AuthController.isWheelchair(context);
        String vehicles = getVehicles(context, userId);
        if (vehicles.equals(""))
            return new Profile(userId, isHandicapped, null);

        String[] vehiclesSplitted = vehicles.split("@");
        ArrayList<Vehicle> vehicleArrayList = new ArrayList<>();
        for (String vehicleUser : vehiclesSplitted) {
            String matricula = vehicleUser.split("#")[0];
            boolean particular = (vehicleUser.split("#")[1]).equals("true");

            vehicleArrayList.add(new Vehicle(matricula, particular));
        }

        return new Profile(userId, isHandicapped, vehicleArrayList);
    }

    public static String helperConversionVehicles(ArrayList<EditText> vehiclesArray, ArrayList<CheckBox> particularArray) {
        StringBuilder vehicleParticularData = new StringBuilder();
        for (int i = 0; i < vehiclesArray.size(); ++i) {
            vehicleParticularData
                    .append(vehiclesArray.get(i).getText().toString())
                    .append("#")
                    .append(particularArray.get(i).isChecked());
            if (i < vehiclesArray.size() - 1) {
                vehicleParticularData.append("@");
            }
        }
        return vehicleParticularData.toString();
    }

    // vehicle1#particular1@vehicle2#particula2
    public static void updateVehicles(Context context, int username, String vehiclesParticularArray) {
        SharedPreferencesController.setStringValue(context, KEY_USER_VEHICLES + username, vehiclesParticularArray);
    }

    public static String getVehicles(Context context, int username) {
        return SharedPreferencesController.getStringValue(context, KEY_USER_VEHICLES + username, "");
    }
}
