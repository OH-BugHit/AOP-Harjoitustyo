package com.oh.ready4play.minipelit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.oh.ready4play.Peli;
import com.oh.ready4play.R;

/**
 * Huora -peli
 * @version 1.0
 * @author Olli Hilke
 */
public class Huora extends Fragment {
    /**
     * Luokan parametriton alustaja
     */
    public Huora() {super(R.layout.fragment_huora);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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