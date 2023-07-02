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

public class WouldYouRather extends Fragment {

    public WouldYouRather() {super(R.layout.fragment_would_you_rather);}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_would_you_rather, container, false);

        String pelaajaVuorossa = " " + Peli.pelaajat.get(Peli.vuorossaPelaaja).pelaajanimi;

        TextView tvPelaaja = view.findViewById(R.id.tvPelaajaNimi_WouldYouRather);

        tvPelaaja.setText(pelaajaVuorossa);

        Button btJatkaPelia = view.findViewById(R.id.btJatkaPelia_WouldYouRather);

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