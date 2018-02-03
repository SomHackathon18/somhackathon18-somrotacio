package com.sommobilitat.hackathon.somhackathon2018;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sommobilitat.hackathon.somhackathon2018.controllers.APIController;
import com.sommobilitat.hackathon.somhackathon2018.controllers.ProfileController;
import com.sommobilitat.hackathon.somhackathon2018.controllers.VolleyController;
import com.sommobilitat.hackathon.somhackathon2018.models.Parking;
import com.sommobilitat.hackathon.somhackathon2018.models.Profile;
import com.sommobilitat.hackathon.somhackathon2018.models.Vehicle;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class DashboardFragment extends Fragment {
    private static final String TAG = AuthFragment.class.getSimpleName();
    private View rootView;
    private LinearLayout layoutCheckIn;
    private LinearLayout layoutCheckStatus;
    private LinearLayout layoutCheckOut;
    private EditText idParkingEditText;
    private TextView matriculaCurrentTextView;
    private TextView parkingTimeCurrentTextView;
    private TextView parkingTimeStartTextView;
    private TextView parkingIDTextView;
    private Spinner vehiclesSpinner;
    private Button goCheckInButton;
    private Button goCheckOutButton;

    private Profile profile;
    private Vehicle selectedVehicle;
    private Parking currentParking;
    private ArrayAdapter<String> arrayAdapterMatricula;
    private CountDownTimer countDownTimer;

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        setUpElements();
        setUpListeners();

        profile = ProfileController.getProfileFromDB(getContext(), ProfileController.INDEX_BASE_PROFILE);
        if (profile.getVehiclesArrayList() != null && !profile.getVehiclesArrayList().isEmpty()) {
            String[] items = new String[profile.getVehiclesArrayList().size()];
            for (int i = 0; i < profile.getVehiclesArrayList().size(); ++i) {
                items[i] = profile.getVehiclesArrayList().get(i).getMatricula();
            }
            arrayAdapterMatricula = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
            vehiclesSpinner.setAdapter(arrayAdapterMatricula);

            setLayoutForCheckIn();
        }

        return rootView;
    }

    private void setUpElements() {
        layoutCheckIn = (LinearLayout) rootView.findViewById(R.id.panel_check_in_layout);
        layoutCheckStatus = (LinearLayout) rootView.findViewById(R.id.panel_check_status_layout);
        layoutCheckOut = (LinearLayout) rootView.findViewById(R.id.panel_check_out_layout);
        idParkingEditText = (EditText) rootView.findViewById(R.id.panel_check_in_parkingid_edittext);
        goCheckInButton = (Button) rootView.findViewById(R.id.bt_go_check_in);
        goCheckOutButton = (Button) rootView.findViewById(R.id.bt_go_check_out);
        parkingTimeStartTextView = (TextView) rootView.findViewById(R.id.tv_status_time_start);
        parkingIDTextView = (TextView) rootView.findViewById(R.id.tv_status_parking_current);
        parkingTimeCurrentTextView = (TextView) rootView.findViewById(R.id.tv_status_time_current);
        matriculaCurrentTextView = (TextView) rootView.findViewById(R.id.tv_status_matricula_current);
        vehiclesSpinner = (Spinner) rootView.findViewById(R.id.panel_check_in_matriculas_spinner);
    }

    private void setUpListeners() {
        vehiclesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedVehicle = profile.getVehicleFromMatricula(arrayAdapterMatricula.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        goCheckInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendParkingCheckIn(Integer.parseInt(idParkingEditText.getText().toString()), selectedVehicle);
            }
        });

        goCheckOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendParkingCheckOut(currentParking.getIdServer());
            }
        });
    }

    private void sendParkingCheckIn(int idOfCiDZone, final Vehicle selectedVehicle) {
        if (selectedVehicle != null) {
            JSONObject postParams = new JSONObject();
            try {
                int tipusDeVehicle;
                if (profile.isHandicapped()) {
                    tipusDeVehicle = 3;
                } else if (selectedVehicle.isParticular()) {
                    tipusDeVehicle = 2;
                } else {
                    tipusDeVehicle = 1;
                }
                postParams.put(
                        APIController.ENDPOINT_CHECK_IN_PARKING_VEHICLE_PARAM,
                        selectedVehicle.getMatricula());
                postParams.put(
                        APIController.ENDPOINT_CHECK_IN_PARKING_TIPUS_PARAM,
                        tipusDeVehicle);
                postParams.put(
                        APIController.ENDPOINT_CHECK_IN_PARKING_PARK_ID_PARAM,
                        idOfCiDZone);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, APIController.getURLForCheckIn(), postParams,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e(TAG, response.toString());

                            try {
                                int idParkingServer = response.getInt("id");
                                int idParking = response.getInt("parkingArea");
                                String startTime = response.getString("startTime");

                                currentParking = new Parking(profile, selectedVehicle, idParking, idParkingServer, startTime);
                                setLayoutForCheckOut();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(TAG, "That didn't work!");
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type", "application/json");
                    return params;
                }
            };

            VolleyController.getInstance(getContext()).addToQueue(jsonObjectRequest);
        }
    }

    private void sendParkingCheckOut(int idOfServerZona) {
        if (selectedVehicle != null) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, APIController.getURLForCheckOut(idOfServerZona), null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e(TAG, response.toString());
                            if (countDownTimer != null) {
                                countDownTimer.cancel();
                            }
                            setLayoutForCheckIn();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(TAG, "That didn't work!");
                        }
                    });

            VolleyController.getInstance(getContext()).addToQueue(jsonObjectRequest);
        }
    }

    private void setLayoutForCheckIn() {
        layoutCheckIn.setVisibility(View.VISIBLE);
        layoutCheckOut.setVisibility(View.GONE);
        layoutCheckStatus.setVisibility(View.GONE);
    }

    private void setLayoutForCheckOut() {
        layoutCheckStatus.setVisibility(View.VISIBLE);
        layoutCheckOut.setVisibility(View.VISIBLE);
        layoutCheckIn.setVisibility(View.GONE);

        parkingTimeStartTextView.setText("" + currentParking.getStartTime());
        parkingIDTextView.setText("" + currentParking.getIdParking());
        matriculaCurrentTextView.setText("" + currentParking.getVehicle().getMatricula());

        countDownTimer = new CountDownTimer(15 * 60 * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                if (getActivity() != null && isAdded()) {
                    long minutes = (millisUntilFinished / 1000) / 60;
                    long seconds = (millisUntilFinished / 1000) % 60;
                    parkingTimeCurrentTextView.setText(minutes + "min " + seconds + "sec");
                }
            }

            public void onFinish() {
            }

        }.start();
    }
}
