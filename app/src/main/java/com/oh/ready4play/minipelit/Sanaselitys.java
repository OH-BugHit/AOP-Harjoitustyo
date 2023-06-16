package com.oh.ready4play.minipelit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.oh.ready4play.MainActivity;
import com.oh.ready4play.Peli;
import com.oh.ready4play.R;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class Sanaselitys extends Fragment {
    private final Random random = new Random();
    ArrayList<String> sanasto = new ArrayList<>();
    ArrayList<String> glossary = new ArrayList<>();

    private TextView tvAika;
    private TextView tvPistenaytto;
    private TextView tvSana;
    private int aika = 60;
    private int pistemaara = 0;
    volatile boolean sanapeliOhi = false;

    public Sanaselitys() {super(R.layout.fragment_sanaselitys);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sanaselitys, container, false);

        teeSanaluettelo();

        tvSana = view.findViewById(R.id.tvSana_Sanaselitys);
        tvPistenaytto = view.findViewById(R.id.tvPisteNaytto_Sanaselitys);
        tvAika = view.findViewById(R.id.tvAika_Sanaselitys);

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
            etenePelissa(btOhita, btOikein, btJatkaPelia);
        });

        btOhita.setOnClickListener(e -> {
            pistemaara -= 0.5;
            tvPistenaytto.setText(pistemaara);
            etenePelissa(btOhita, btOikein, btJatkaPelia);
        });

        btAloita.setOnClickListener(e -> {
            btAloita.setVisibility(View.INVISIBLE);
            btOhita.setVisibility(View.VISIBLE);
            btOikein.setVisibility(View.VISIBLE);
            kaynnistaAjastin();
            seuraavaSana();
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

    private void etenePelissa(Button btOhita, Button btOikein, Button btJatkaPelia) {
        if (sanapeliOhi) {
            btOikein.setVisibility(View.INVISIBLE);
            btOhita.setVisibility(View.INVISIBLE);
            tvSana.setText(R.string.text_lopputekstiDictionary + pistemaara);
            btJatkaPelia.setVisibility(View.VISIBLE);
        } else {
            seuraavaSana();
        }
    }

    private void kaynnistaAjastin() {
        Thread t1 = new Thread(() -> {
            while (aika > 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                aika -= 1;
                MainActivity.INSTANCE.runOnUiThread(Sanaselitys.this::paivitaAika);
            }
            sanapeliOhi = true;
        });
        t1.start();
    }

    private void paivitaAika() {
        tvAika.setText(aika);
    }

    private void teeSanaluettelo() {
        sanasto.add("Margariini");
        sanasto.add("Tomaatti");
        sanasto.add("Lasi");

        glossary.add("Butter");
        glossary.add("Tomato");
        glossary.add("Glass");
    }

    private void seuraavaSana() {
        String kieli = Locale.getDefault().getISO3Language();
        System.out.println("Valittu kieli on: " + kieli);
        String valittuSana;
        if (!sanasto.isEmpty() || !glossary.isEmpty()) {
            switch (kieli) {
                case "fin" -> {
                    valittuSana = sanasto.remove(random.nextInt(sanasto.size()));
                    tvSana.setText(valittuSana);
                }
                default -> {
                    valittuSana = glossary.remove(random.nextInt(glossary.size()));
                    tvSana.setText(valittuSana);
                }
            }
        } else tvSana.setText(R.string.text_blank);
    }
}