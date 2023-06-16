package com.oh.ready4play.minipelit;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.oh.ready4play.Peli;
import com.oh.ready4play.R;

import java.util.ArrayList;

public class FuckTheDealer extends Fragment {
    ArrayList<Drawable> kortit = new ArrayList<>();
    ImageView ivKortti;
    private TextView tvOhje1;
    private TextView tvOhje2;
    private TextView tvOhje3;
    private TextView tvOhje4;
    private TextView tvPelaaja;
    private TextView tvSeuraavaPelaaja;

    public FuckTheDealer() {super(R.layout.fragment_fuck_the_dealer);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        alustaKortit();
        View view = inflater.inflate(R.layout.fragment_fuck_the_dealer, container, false);

        ivKortti = view.findViewById(R.id.ivKortti_FtheDealer);

        ivKortti.setVisibility(View.INVISIBLE);

        Button btJatkaPelia = view.findViewById(R.id.btJatkaPelia_FtheDealer);
        Button btKurkista = view.findViewById(R.id.btPeek_FtheDealer);
        Button btSeuraavaKortti = view.findViewById(R.id.btNextCard_FtheDealer);
        Button btAloita = view.findViewById(R.id.btAloita_FtheDealer);

        btKurkista.setVisibility(View.INVISIBLE);
        btSeuraavaKortti.setVisibility(View.INVISIBLE);

        tvOhje1 = view.findViewById(R.id.tvOhjeistus_FtheDealer);
        tvOhje2 = view.findViewById(R.id.tvOhjeistus2_FtheDealer);
        tvOhje3 = view.findViewById(R.id.tvOhjeistus4_FtheDealer);
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
            ivKortti.setVisibility(View.VISIBLE);
            seuraavaKortti();
        });

        btJatkaPelia.setOnClickListener(e -> {
            Peli.seuraavaVuoro = true;
            Peli.fragmentManager.beginTransaction()
                    .remove(this)
                    .setReorderingAllowed(true)
                    .commit();
        });


        return view;
    }

    private void seuraavaKortti() {
    }

    private void alustaKortit() {
        //TODO: Aseta tänne kaikki kortit
    }
}