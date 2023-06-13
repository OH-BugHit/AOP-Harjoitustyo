package com.oh.ready4play;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * poistuu sovelluksesta
 */
public class QuitFragment extends AppCompatActivity {


    public QuitFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_quit);
        quit();
    }

    public void quit() {
        finish();
    };
}