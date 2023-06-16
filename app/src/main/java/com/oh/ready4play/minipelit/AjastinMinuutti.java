package com.oh.ready4play.minipelit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oh.ready4play.R;


public class AjastinMinuutti extends Fragment {

    public AjastinMinuutti() {super(R.layout.fragment_ajastin_minuutti);}

    public static void kaynnistaAjastin() {
        //TODO: TEE TÄNNE AJASTIN JOKA PÄIVITTÄÄ AIKAA. KUN 0 NIIN ASETTAA SANASELITYS sanapeliOhi = TRUE
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ajastin_minuutti, container, false);
    }
}