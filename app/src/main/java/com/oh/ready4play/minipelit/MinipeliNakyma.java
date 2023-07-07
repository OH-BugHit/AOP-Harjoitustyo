package com.oh.ready4play.minipelit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oh.ready4play.R;

/**
 * Minipeli asetetaan näkyviin tähän näkymään
 * @version 1.0
 * @author Olli Hilke
 */
public class MinipeliNakyma extends Fragment {

    public MinipeliNakyma() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_minipeli_nakyma, container, false);
    }
}
