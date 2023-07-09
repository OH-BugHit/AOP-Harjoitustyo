package com.oh.ready4play.minipelit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.oh.ready4play.Peli;
import com.oh.ready4play.R;

/**
 * Kasa -peli
 * @version 1.0
 * @author Olli Hilke
 */
public class Kasa extends Fragment {
    /**
     * Luokan parametriton alustaja
     */
    public Kasa() {super(R.layout.fragment_kasa);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kasa, container, false);

        TextView tvPelaaja = view.findViewById(R.id.tvPelaajaNimi_Kasa);
        tvPelaaja.setText(Peli.pelaajat.get(Peli.vuorossaPelaaja).pelaajanimi + " ");

        Button btJatkaPelia = view.findViewById(R.id.btJatkaPelia_Kasa);

        Peli.tehtavaFail = true;

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