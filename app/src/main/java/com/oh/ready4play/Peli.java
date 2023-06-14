package com.oh.ready4play;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Peli extends Fragment {

    public static ArrayList<Pelaaja> pelaajat;
    private boolean hampuriKlikattu = false;

    public Peli() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_peli, container, false);

        pelaajat = UusiPeli.itemArrayList;

        TextView tvOhitaPelaajanimi = view.findViewById(R.id.tvOhitaJatkossa);
        TextView tvPaavalikko = view.findViewById(R.id.tvPaavalikko);
        TextView tvLopeta = view.findViewById(R.id.tvLopeta);

        tvOhitaPelaajanimi.setVisibility(View.INVISIBLE);
        tvPaavalikko.setVisibility(View.INVISIBLE);
        tvLopeta.setVisibility(View.INVISIBLE);

        ImageView ivHampuri = view.findViewById(R.id.ivHampuri_peli);

        ivHampuri.setOnClickListener(e -> {
            if (hampuriKlikattu) {
                hampuriKlikattu = false;
                tvOhitaPelaajanimi.setVisibility(View.INVISIBLE);
                tvPaavalikko.setVisibility(View.INVISIBLE);
                tvLopeta.setVisibility(View.INVISIBLE);
            } else {
                hampuriKlikattu = true;
                tvOhitaPelaajanimi.setVisibility(View.VISIBLE);
                tvPaavalikko.setVisibility(View.VISIBLE);
                tvLopeta.setVisibility(View.VISIBLE);
            }
        });

        ImageView ivNappulaVuorossa = view.findViewById(R.id.ivNappulaVuorossa);

        Button btHeitaNoppa = view.findViewById(R.id.btHeita_Peli);
        Button btOhita = view.findViewById(R.id.btOhita_Peli);

        TextView tvVuorossaPelaaja = view.findViewById(R.id.tvPelaajaNimi_Peli);



        return view;
    }
}