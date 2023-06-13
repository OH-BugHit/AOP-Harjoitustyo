package com.oh.ready4play;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Asetukset extends Fragment {

    private static String asetukset = "";
    private static String uudetAsetukset = "";
    public Asetukset() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_asetukset, container, false);

        Button btPaluu = view.findViewById(R.id.btPaluu_Asetukset);
        Button btVahvista = view.findViewById(R.id.btVahvista_Asetukset);



        btPaluu.setOnClickListener(e -> {
            Navigation.findNavController(view).navigate(AsetuksetDirections.actionAsetuksetToAlkuvalikko());
        });

        btVahvista.setOnClickListener(e -> {
            tallenna();
            Navigation.findNavController(view).navigate(AsetuksetDirections.actionAsetuksetToAlkuvalikko());
        });


        return view;
    }

    //TODO: Tee tallennus tänne
    private void tallenna() {
    }
}