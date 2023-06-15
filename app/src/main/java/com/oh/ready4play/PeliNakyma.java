package com.oh.ready4play;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PeliNakyma extends Fragment {

    public PeliNakyma() {super(R.layout.fragment_peli_nakyma);}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_peli_nakyma, container, false);


        return view;
    }
}