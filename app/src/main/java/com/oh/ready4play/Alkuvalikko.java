package com.oh.ready4play;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Alkuvalikko-fragment sisältää alkuvalikon
 * @version 1.0
 * @author Olli Hilke
 */
public class Alkuvalikko extends Fragment {
    /**
     * Asetusten tallennukseen
     */
    protected static SharedPreferences sharedPref;
    protected static boolean jatkaPelia = false;
    /**
     * Luokan parametriton alustaja
     */
    public Alkuvalikko() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_alkuvalikko, container, false);
        Context context = getActivity();
        /*
        sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

         */
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        Button btAloitaPeli = view.findViewById(R.id.btAloitaPeli_alkuvalikko);
        Button btAsetukset = view.findViewById(R.id.btAsetukset_alkuvalikko);
        Button btQuit = view.findViewById(R.id.btLopeta_alkuvalikko);
        Button btContinue = view.findViewById(R.id.btJatkaPelia_alkuvalikko);

        System.out.println("ensin se on" + jatkaPelia);
        jatkaPelia = sharedPref.getBoolean("canContinue",false);
        System.out.println("Tässä vaiheessa se on: " + jatkaPelia);

        btContinue.setVisibility(View.INVISIBLE);
        if (jatkaPelia) {
            btContinue.setVisibility(View.VISIBLE);
        }

        btContinue.setOnClickListener(e -> {
            Navigation.findNavController(view).navigate(R.id.action_alkuvalikko_to_peli);
        });

        btAloitaPeli.setOnClickListener(e -> {
            Navigation.findNavController(view).navigate(R.id.action_alkuvalikko_to_uusiPeli);
        });

        btAsetukset.setOnClickListener(e -> {
            Navigation.findNavController(view).navigate(R.id.action_alkuvalikko_to_asetukset);
        });

        btQuit.setOnClickListener(e -> {
            Navigation.findNavController(view).navigate(R.id.action_alkuvalikko_to_quitFragment2);
        });

        return view;
    }
}