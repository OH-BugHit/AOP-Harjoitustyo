package com.oh.ready4play;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Random;

public class Noppa extends Fragment {
    private static Random random = new Random();
    private static ImageView noppaKuva;

    public Noppa() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_noppa, container, false);
        noppaKuva = view.findViewById(R.id.ivNoppa);
        noppaKuva.setVisibility(View.INVISIBLE);
        return view;
    }

    /**
     * Heittää nopan ja asettaa nopan kuvan näkyviin
     * @return Palauttaa heiton arvon (mukaan laskettu asetuksissa määritelty asetus)
     */
    public static int heitaNoppaa() {
        noppaKuva.setVisibility(View.VISIBLE);
        // TOTEUTA TÄÄ RIVI KUN ON TALLENNUS JA LATAUS! int heitto = random.nextInt(6) + 1 + UusiPeli.asetukset.noppaPlus;
        int heitto = random.nextInt(6) + 1;
        switch (heitto) {
            case 1 -> noppaKuva.setImageResource(R.drawable.noppa1);
            case 2 -> noppaKuva.setImageResource(R.drawable.noppa2);
            case 3 -> noppaKuva.setImageResource(R.drawable.noppa3);
            case 4 -> noppaKuva.setImageResource(R.drawable.noppa4);
            case 5 -> noppaKuva.setImageResource(R.drawable.noppa5);
            case 6 -> noppaKuva.setImageResource(R.drawable.noppa6);
            case 7 -> noppaKuva.setImageResource(R.drawable.noppa7);
            case 8 -> noppaKuva.setImageResource(R.drawable.noppa8);
            case 9 -> noppaKuva.setImageResource(R.drawable.noppa9);
            case 10 -> noppaKuva.setImageResource(R.drawable.noppa10);
            case 11 -> noppaKuva.setImageResource(R.drawable.noppa11);
        }
        if (Peli.pelaajat.get(Peli.vuorossaPelaaja).bonusAskeleet) {
            heitto += 3;
            Peli.pelaajat.get(Peli.vuorossaPelaaja).bonusAskeleet = false;
        }
        return heitto ;
    }
}