package com.oh.ready4play.minipelit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oh.ready4play.R;

public class Ravit extends Fragment {

    public Ravit() {super(R.layout.fragment_ravit);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ravit, container, false);

        return view;
    }
}