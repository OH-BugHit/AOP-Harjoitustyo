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
 * Tytöt vs Pojat -peli
 * @version 1.0
 * @author Olli Hilke
 */
public class TytotVsPojat extends Fragment {


    public TytotVsPojat() {super(R.layout.fragment_tytot_vs_pojat);}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tytot_vs_pojat, container, false);

        Button btJatkaPelia = view.findViewById(R.id.btJatkaPelia_TytotVsPojat);

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