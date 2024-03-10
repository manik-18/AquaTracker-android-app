package com.majorproject.aquatracker.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;

import com.majorproject.aquatracker.BackgroundService;
import com.majorproject.aquatracker.R;

public class HomeFragment extends Fragment {

    private Button b1,b2;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        b1=view.findViewById(R.id.button_yes);
        b2=view.findViewById(R.id.button_no);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBackgroundService();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopBackgroundService();
            }
        });
        return view;
    }

    private void stopBackgroundService() {
        Intent serviceIntent = new Intent(getActivity(), BackgroundService.class);
        getActivity().stopService(serviceIntent);
    }

    private void startBackgroundService() {
        Intent serviceIntent = new Intent(getActivity(), BackgroundService.class);
        getActivity().startService(serviceIntent);
    }
}
