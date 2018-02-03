package com.sommobilitat.hackathon.somhackathon2018;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sommobilitat.hackathon.somhackathon2018.controllers.AuthController;
import com.sommobilitat.hackathon.somhackathon2018.controllers.ProfileController;

import java.util.ArrayList;


public class ProfileFragment extends Fragment {
    private View rootView;
    private TextView userTextView;
    private LinearLayout vehiclesLinearLayout;
    private Button updateProfileButton;
    private Button logoutButton;
    private ImageView addVehicleImageView;

    private ArrayList<EditText> vehicleArray;
    private ArrayList<CheckBox> particularArray;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        setUpElements();
        setUpListeners();

        userTextView.setText("getUsername");

        fillVehicles();

        return rootView;
    }

    private void fillVehicles() {
        vehicleArray = new ArrayList<>();
        particularArray = new ArrayList<>();
        String vehiclesInfo = ProfileController.getVehicles(getContext(), ProfileController.INDEX_BASE_PROFILE);
        if (vehiclesInfo.equals(""))
            return;
        String[] vehiclesPerUser = vehiclesInfo.split("@");

        for (String vehicleUser : vehiclesPerUser) {
            String matricula = vehicleUser.split("#")[0];
            boolean particular = (vehicleUser.split("#")[1]).equals("true");

            drawVehicle(matricula, particular);
        }
    }

    private void drawVehicle(String matricula, boolean particular) {
        EditText vehicleEditText = new EditText(getContext());
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(480, ViewGroup.LayoutParams.WRAP_CONTENT);
        vehicleEditText.setLayoutParams(p);
        vehicleEditText.setHint(getString(R.string.profile_vehicle_label));
        vehicleEditText.setText(matricula);

        CheckBox vehicleCheckBox = new CheckBox(getContext());
        LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        vehicleCheckBox.setLayoutParams(p2);
        vehicleCheckBox.setChecked(particular);

        LinearLayout baseVehicleLayout = new LinearLayout(getContext());
        baseVehicleLayout.addView(vehicleEditText);
        baseVehicleLayout.addView(vehicleCheckBox);

        vehiclesLinearLayout.addView(baseVehicleLayout);
        vehicleArray.add(vehicleEditText);
        particularArray.add(vehicleCheckBox);
    }

    private void setUpElements() {
        userTextView = (TextView) rootView.findViewById(R.id.profile_username_text);
        vehiclesLinearLayout = (LinearLayout) rootView.findViewById(R.id.vehicles_layout);
        addVehicleImageView = (ImageView) rootView.findViewById(R.id.profile_add_vehicle);
        updateProfileButton = (Button) rootView.findViewById(R.id.bt_update_profile);
        logoutButton = (Button) rootView.findViewById(R.id.bt_logout);
    }

    private void setUpListeners() {
        addVehicleImageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                drawVehicle("", false);
            }
        });

        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getContext(), "Profile saved correctly", Toast.LENGTH_SHORT).show();
                ProfileController.updateVehicles(getContext(),
                        ProfileController.INDEX_BASE_PROFILE, ProfileController.helperConversionVehicles(vehicleArray, particularArray));
                ((MainActivity) getActivity()).reloadMainActivity(R.id.navigation_profile);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getContext(), "Logged out OK", Toast.LENGTH_SHORT).show();
                ProfileController.updateVehicles(getContext(), ProfileController.INDEX_BASE_PROFILE, "");
                AuthController.setUserLogged(getContext(), false);
                ((MainActivity) getActivity()).reloadMainActivity(R.id.navigation_profile);
            }
        });
    }
}
