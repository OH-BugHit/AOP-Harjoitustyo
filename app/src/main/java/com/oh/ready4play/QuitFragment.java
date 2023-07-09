package com.oh.ready4play;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Sovelluksesta poistumisen näkymä
 * @version 1.0
 * @author Olli Hilke
 */
public class QuitFragment extends Fragment {
    /**
     * Luokan parametriton alustaja.
     */
    public QuitFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quit, container, false);

        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            quit();
        });
        t1.start();
        return view;
    }

    /**
     * Sovelluksen sammutus
     */
    private void quit() {
        MainActivity.INSTANCE.finish();
        System.exit(0);
    };
}