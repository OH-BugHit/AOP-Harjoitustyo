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


public class KolmeShottia extends Fragment {

    public KolmeShottia() {super(R.layout.fragment_kolme_shottia);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_kolme_shottia, container, false);

        String pelaajaVuorossa = Peli.pelaajat.get(Peli.vuorossaPelaaja).pelaajanimi;

        TextView tvPelaaja = view.findViewById(R.id.tvPelaajaVuorossa_3Shottia);

        tvPelaaja.setText(pelaajaVuorossa);

        Button btJatkaPelia = view.findViewById(R.id.btJatkaPelia_3Shottia);

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