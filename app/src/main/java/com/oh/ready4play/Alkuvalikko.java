package com.oh.ready4play;

import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Alkuvalikko-fragment sisältää alkuvalikon
 * @version 0.1
 */
public class Alkuvalikko extends Fragment {

    public Alkuvalikko() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        lataaAsetukset();
        View view = inflater.inflate(R.layout.fragment_alkuvalikko, container, false);

        Button btAloitaPeli = view.findViewById(R.id.btAloitaPeli_alkuvalikko);
        Button btAsetukset = view.findViewById(R.id.btAsetukset_alkuvalikko);
        Button btQuit = view.findViewById(R.id.btLopeta_alkuvalikko);

        btAsetukset.setOnClickListener(e -> {
            Navigation.findNavController(view).navigate(R.id.action_alkuvalikko_to_asetukset);
        });

        btQuit.setOnClickListener(e -> {
            Navigation.findNavController(view).navigate(R.id.action_alkuvalikko_to_quitFragment);
        });


        return view;
    }

    private void lataaAsetukset() {
    }
}