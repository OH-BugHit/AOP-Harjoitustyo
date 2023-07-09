package com.oh.ready4play;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Pelilaudan näkymä
 * @version 1.0
 * @author Olli Hilke
 */
public class PeliNakyma extends Fragment {
    /**
     * Luokan alustaja. Sisältää PeliNakyma-layoutin
     */
    public PeliNakyma() {super(R.layout.fragment_peli_nakyma);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_peli_nakyma, container, false);
    }
}