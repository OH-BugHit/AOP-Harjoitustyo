package com.oh.ready4play.minipelit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.oh.ready4play.Peli;
import com.oh.ready4play.R;

public class Huora extends Fragment {

    public Huora() {super(R.layout.fragment_huora);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_huora, container, false);

        Button btJatkaPelia = view.findViewById(R.id.btJatkaPelia_Huora);

        btJatkaPelia.setOnClickListener(e -> {
            Peli.seuraavaVuoro = true;
            Peli.fragmentManager.beginTransaction()
                    .remove(this)
                    .setReorderingAllowed(true)
                    .commit();
        });

        return view;
    }
}