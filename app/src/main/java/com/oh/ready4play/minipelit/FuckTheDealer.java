package com.oh.ready4play.minipelit;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.oh.ready4play.Peli;
import com.oh.ready4play.R;

import java.util.Random;

/**
 * Fuck the Dealer -peli
 * @version 1.0
 * @author Olli Hilke
 */
public class FuckTheDealer extends Fragment {
    /**
     * Arvattava kortti
     */
    private ImageView ivKortti;
    /**
     * Kortti, jolla peitetään arvottava kortti
     */
    private ImageView ivPeitto;
    /**
     * TextView ohjeiden esittämiseen
     */
    private TextView tvOhje1;
    /**
     * TextView ohjeiden esittämiseen
     */
    private TextView tvOhje2;
    /**
     * TextView ohjeiden esittämiseen
     */
    private TextView tvOhje3;
    /**
     * TextView ohjeiden esittämiseen
     */
    private TextView tvOhje4;
    /**
     * TextView vuorossaolevan pelaajan näyttämiseen
     */
    private TextView tvPelaaja;
    /**
     * TextView seuraavan pelaajan nimen esittämiseen
     */
    private TextView tvSeuraavaPelaaja;
    private final Random random = new Random();
    public FuckTheDealer() {super(R.layout.fragment_fuck_the_dealer);}

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fuck_the_dealer, container, false);

        ivKortti = view.findViewById(R.id.ivKortti_FtheDealer);
        ivPeitto = view.findViewById(R.id.ivKorttiPeitto_FtheDealer);

        ivKortti.setVisibility(View.INVISIBLE);
        ivPeitto.setVisibility(View.INVISIBLE);
        ivPeitto.setImageResource(R.drawable.tausta);

        Button btJatkaPelia = view.findViewById(R.id.btJatkaPelia_FtheDealer);
        btJatkaPelia.setVisibility(View.INVISIBLE);
        Button btKurkista = view.findViewById(R.id.btPeek_FtheDealer);
        Button btSeuraavaKortti = view.findViewById(R.id.btNextCard_FtheDealer);
        Button btAloita = view.findViewById(R.id.btAloita_FtheDealer);

        btKurkista.setVisibility(View.INVISIBLE);
        btSeuraavaKortti.setVisibility(View.INVISIBLE);

        tvOhje1 = view.findViewById(R.id.tvOhjeistus_FtheDealer);
        tvOhje2 = view.findViewById(R.id.tvOhjeistus2_FtheDealer);
        tvOhje3 = view.findViewById(R.id.tvOhjeistus3_FtheDealer);
        tvOhje4 = view.findViewById(R.id.tvOhjeistus4_FtheDealer);
        tvPelaaja = view.findViewById(R.id.tvPelaajaVuorossa_FtheDealer);
        tvPelaaja.setText(Peli.pelaajat.get(Peli.vuorossaPelaaja).pelaajanimi);
        tvSeuraavaPelaaja = view.findViewById(R.id.tvSeuraavaPelaaja_FtheDealer);
        String asetettavaSeuraavaNimi;
        if (Peli.vuorossaPelaaja == Peli.pelaajat.size()-1) {
            asetettavaSeuraavaNimi = Peli.pelaajat.get(0).pelaajanimi + " ";
            tvSeuraavaPelaaja.setText(asetettavaSeuraavaNimi);
        } else {
            asetettavaSeuraavaNimi = Peli.pelaajat.get(Peli.vuorossaPelaaja + 1).pelaajanimi + " ";
            tvSeuraavaPelaaja.setText(asetettavaSeuraavaNimi);
        }


        btAloita.setOnClickListener(e -> {
            tvOhje1.setVisibility(View.INVISIBLE);
            tvOhje2.setVisibility(View.INVISIBLE);
            tvOhje3.setVisibility(View.INVISIBLE);
            tvOhje4.setVisibility(View.INVISIBLE);
            tvPelaaja.setVisibility(View.INVISIBLE);
            tvSeuraavaPelaaja.setVisibility(View.INVISIBLE);
            btKurkista.setVisibility(View.VISIBLE);
            btSeuraavaKortti.setVisibility(View.VISIBLE);
            ivPeitto.setVisibility(View.VISIBLE);
            ivKortti.setVisibility(View.VISIBLE);
            btAloita.setVisibility(View.INVISIBLE);
            btJatkaPelia.setVisibility(View.VISIBLE);
            seuraavaKortti();
        });

        btJatkaPelia.setOnClickListener(e -> {
            Peli.seuraavaVuoro = true;
            Peli.fragmentManager.beginTransaction()
                    .remove(this)
                    .setReorderingAllowed(true)
                    .commit();
        });

        View.OnTouchListener kurkista = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN -> {
                        ivPeitto.setY(ivKortti.getY() + 80);
                        ivPeitto.setX(ivKortti.getX() - 100);
                    }
                    case MotionEvent.ACTION_UP -> {
                        ivPeitto.setY(ivKortti.getY());
                        ivPeitto.setX(ivKortti.getX());
                    }
                }
                return false;
            }
        };

        btKurkista.setOnTouchListener(kurkista);
        btSeuraavaKortti.setOnClickListener(e -> {
            seuraavaKortti();
        });

        return view;
    }

    /**
     * Arpoo seuraavan kortin ja asettaa sen kortiksi.
     */
    private void seuraavaKortti() {
        ivKortti.setImageDrawable(Peli.pakka.get(random.nextInt(Peli.pakka.size())).kuva);
    }
}