package com.sommobilitat.hackathon.somhackathon2018.controllers;

import android.util.Log;

public class APIController {
    private static final String BASE_URL = "http://178.62.30.127";
    private static final String ENDPOINT_CHECK_IN_PARKING = "/parkings";
    private static final String ENDPOINT_CHECK_OUT_PARKING = "/endtime";

    public static final String ENDPOINT_CHECK_IN_PARKING_VEHICLE_PARAM = "vehicle";
    public static final String ENDPOINT_CHECK_IN_PARKING_PARK_ID_PARAM = "parkingArea";

    public static String getURLForCheckIn() {
        return BASE_URL + ENDPOINT_CHECK_IN_PARKING;
    }

    public static String getURLForCheckOut(int urlOfPark) {
        Log.e("TEST", BASE_URL + ENDPOINT_CHECK_IN_PARKING + "/" + urlOfPark + ENDPOINT_CHECK_OUT_PARKING);
        return BASE_URL + ENDPOINT_CHECK_IN_PARKING + "/" + urlOfPark + ENDPOINT_CHECK_OUT_PARKING;
    }
}
