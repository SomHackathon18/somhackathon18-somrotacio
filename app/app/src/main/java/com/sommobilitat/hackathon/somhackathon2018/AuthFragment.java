package com.sommobilitat.hackathon.somhackathon2018;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sommobilitat.hackathon.somhackathon2018.controllers.AuthController;
import com.sommobilitat.hackathon.somhackathon2018.controllers.ProfileController;

import java.util.ArrayList;


public class AuthFragment extends Fragment {
    private static final String TAG = AuthFragment.class.getSimpleName();
    private View rootView;
    private Button registerButton;
    private Button loginButton;
    private ArrayList<EditText> vehicleArray;
    private ArrayList<CheckBox> particularArray;

    public static AuthFragment newInstance() {
        return new AuthFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_auth, container, false);

        setUpElements();
        setUpListeners();

        return rootView;
    }

    private void setUpElements() {
        registerButton = (Button) rootView.findViewById(R.id.bt_register);
        loginButton = (Button) rootView.findViewById(R.id.bt_login);
    }

    private void setUpListeners() {
        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showRegisterDialog();
            }
        });
    }

    private void showRegisterDialog() {
        vehicleArray = new ArrayList<>();
        particularArray = new ArrayList<>();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alert_register, null);
        final EditText usernameEditText = (EditText) dialogView.findViewById(R.id.input_register_username);
        final CheckBox wheelchairCheckBox = (CheckBox) dialogView.findViewById(R.id.register_wheelchair_vehicle);
        final LinearLayout addVehicleLayout = (LinearLayout) dialogView.findViewById(R.id.register_vehicle_holder);
        ImageButton addVehicleImageButton = (ImageButton) dialogView.findViewById(R.id.register_add_vehicle);
        addVehicleImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText vehicleEditText = new EditText(getContext());
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(480, ViewGroup.LayoutParams.WRAP_CONTENT);
                vehicleEditText.setLayoutParams(p);
                vehicleEditText.setHint(getString(R.string.profile_vehicle_label));

                CheckBox vehicleCheckBox = new CheckBox(getContext());
                LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                vehicleCheckBox.setLayoutParams(p2);

                LinearLayout baseVehicleLayout = new LinearLayout(getContext());
                baseVehicleLayout.addView(vehicleEditText);
                baseVehicleLayout.addView(vehicleCheckBox);

                addVehicleLayout.addView(baseVehicleLayout);
                vehicleArray.add(vehicleEditText);
                particularArray.add(vehicleCheckBox);
            }
        });
        dialogBuilder.setView(dialogView)
                .setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                ProfileController.updateVehicles(getContext(),
                                        ProfileController.INDEX_BASE_PROFILE, ProfileController.helperConversionVehicles(vehicleArray, particularArray));
                                AuthController.setUsername(getContext(), usernameEditText.getText().toString());
                                AuthController.setWheelchair(getContext(), wheelchairCheckBox.isChecked());
                                AuthController.setUserLogged(getContext(), true);
                                ((MainActivity) getActivity()).reloadMainActivity(R.id.navigation_profile);
                            }
                        }
                )
                .setNegativeButton("Tancar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        }
                );
        dialogBuilder.create();
        dialogBuilder.show();
    }
}
