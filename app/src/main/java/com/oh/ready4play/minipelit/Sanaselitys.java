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

public class Sanaselitys extends Fragment {

    protected TextView tvPistenaytto;
    protected int pistemaara = 0;
    volatile boolean sanapeliOhi;

    public Sanaselitys() {super(R.layout.fragment_sanaselitys);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sanaselitys, container, false);

        TextView tvSana = view.findViewById(R.id.tvSana_Sanaselitys);
        tvPistenaytto = view.findViewById(R.id.tvPisteNaytto_Sanaselitys);

        Button btAloita = view.findViewById(R.id.btAloita_Sanaselitys);
        Button btOhita = view.findViewById(R.id.btOhita_Sanaselitys);
        Button btOikein = view.findViewById(R.id.btOikein_Sanaselitys);
        Button btJatkaPelia = view.findViewById(R.id.btJatkaPelia_Sanaselitys);
        btJatkaPelia.setVisibility(View.INVISIBLE);

        btOhita.setVisibility(View.INVISIBLE);
        btOikein.setVisibility(View.INVISIBLE);


        btOikein.setOnClickListener(e -> {
            pistemaara += 1;
            tvPistenaytto.setText(pistemaara);
            if (sanapeliOhi) {
                btOikein.setVisibility(View.INVISIBLE);
                btOhita.setVisibility(View.INVISIBLE);
                btJatkaPelia.setVisibility(View.VISIBLE);
            } else {
                seuraavaSana();
            }
        });

        btOhita.setOnClickListener(e -> {
            pistemaara -= 0.5;
            tvPistenaytto.setText(pistemaara);
            if (sanapeliOhi) {
                btOikein.setVisibility(View.INVISIBLE);
                btOhita.setVisibility(View.INVISIBLE);
                btJatkaPelia.setVisibility(View.VISIBLE);
            } else {
                seuraavaSana();
            }
        });

        btAloita.setOnClickListener(e -> {
            btAloita.setVisibility(View.INVISIBLE);
            btOhita.setVisibility(View.VISIBLE);
            btOikein.setVisibility(View.VISIBLE);
            AjastinMinuutti.kaynnistaAjastin();
        });


        btJatkaPelia.setOnClickListener(e -> {
            //tvSana.setText(R.string.text_taskDescriptionDictionary);
            //tvPistenaytto.setText("");
            Peli.seuraavaVuoro = true;
            Peli.fragmentManager.beginTransaction()
                    .remove(this)
                    .setReorderingAllowed(true)
                    .commit();
        });

        return view;
    }

    private void seuraavaSana() {
        //TODO: TEE TÄNNE SEURAAVAN SANAN ESITTÄMINEN
    }
}