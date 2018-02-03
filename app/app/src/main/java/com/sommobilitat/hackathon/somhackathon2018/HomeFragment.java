package com.sommobilitat.hackathon.somhackathon2018;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class HomeFragment extends Fragment {
    private View rootView;
    private TextView title;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        setUpElements();
        title.setText(getString(R.string.title_home));

        return rootView;
    }

    private void setUpElements() {
        title = (TextView) rootView.findViewById(R.id.profile_username_label);
    }
}
