package com.oh.ready4play.minipelit;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
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

import java.util.ArrayList;
import java.util.Random;

public class FuckTheDealer extends Fragment {
    ArrayList<Drawable> kortit = new ArrayList<>();
    ImageView ivKortti;
    ImageView ivPeitto;
    private TextView tvOhje1;
    private TextView tvOhje2;
    private TextView tvOhje3;
    private TextView tvOhje4;
    private TextView tvPelaaja;
    private TextView tvSeuraavaPelaaja;
    private final Random random = new Random();
    public FuckTheDealer() {super(R.layout.fragment_fuck_the_dealer);}

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        alustaKortit();
        View view = inflater.inflate(R.layout.fragment_fuck_the_dealer, container, false);

        ivKortti = view.findViewById(R.id.ivKortti_FtheDealer);
        ivPeitto = view.findViewById(R.id.ivKorttiPeitto_FtheDealer);

        ivKortti.setVisibility(View.INVISIBLE);
        ivPeitto.setVisibility(View.INVISIBLE);
        ivPeitto.setImageResource(R.drawable.jokeri1);

        Button btJatkaPelia = view.findViewById(R.id.btJatkaPelia_FtheDealer);
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
        tvSeuraavaPelaaja = view.findViewById(R.id.tvSeuraavaPelaaja_FtheDealer);


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

    private void seuraavaKortti() {
        ivKortti.setImageDrawable(Peli.pakka.get(random.nextInt(Peli.pakka.size())).kuva);
    }

    private void alustaKortit() {
        //TODO: Aseta tänne kaikki kortit
    }
}