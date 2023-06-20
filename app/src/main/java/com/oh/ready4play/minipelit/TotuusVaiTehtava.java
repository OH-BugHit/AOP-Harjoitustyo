package com.oh.ready4play.minipelit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.oh.ready4play.Peli;
import com.oh.ready4play.R;


public class TotuusVaiTehtava extends Fragment {


    public TotuusVaiTehtava() {super(R.layout.fragment_totuus_vai_tehtava);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_totuus_vai_tehtava, container, false);

        TextView tvPelaaja = view.findViewById(R.id.tvPelaajaNimi1_TotuusVaiTehtava);
        TextView tvPelaaja2 = view.findViewById(R.id.tvPelaajaNimi2_TotuusVaiTehtava);
        TextView tvPelaaja3 = view.findViewById(R.id.tvPelaajaNimi3_TotuusVaiTehtava);

        String pelaajaVuorossa = Peli.pelaajat.get(Peli.vuorossaPelaaja).pelaajanimi + " ";
        String pelaajaVuorossa2 = " " + Peli.pelaajat.get(Peli.vuorossaPelaaja).pelaajanimi + " ";

        tvPelaaja.setText(pelaajaVuorossa);
        tvPelaaja2.setText(pelaajaVuorossa2);
        tvPelaaja3.setText(pelaajaVuorossa2);

        Button btJatkaPelia = view.findViewById(R.id.btJatkaPelia_TotuusVaiTehtava);

        btJatkaPelia.setOnClickListener(e -> {
            Peli.seuraavaVuoro = true;
            Peli.fragmentManager.beginTransaction()
                    .remove(this)
                    .setReorderingAllowed(true)
                    .commit();
        });

        return view;
    }
}