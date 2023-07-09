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
 * Kaksi totuutta, yksi valhe -peli
 * @version 1.0
 * @author Olli Hilke
 */
public class KaksiTotuuttaYksiValhe extends Fragment {
    /**
     * Luokan parametriton alustaja
     */
    public KaksiTotuuttaYksiValhe() {super(R.layout.fragment_kaksi_totuutta_yksi_valhe);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kaksi_totuutta_yksi_valhe, container, false);

        Button btJatkaPelia = view.findViewById(R.id.btJatkaPelia_2Totuutta1Valhe);

        btJatkaPelia.setOnClickListener(e -> {
            Peli.pelaajat.get(Peli.vuorossaPelaaja).kaksiTotuutta = true;
            Peli.seuraavaVuoro = true;
            Peli.fragmentManager.beginTransaction()
                    .remove(this)
                    .setReorderingAllowed(true)
                    .commit();
        });

        return view;
    }
}