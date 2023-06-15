package com.oh.ready4play.minipelit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.oh.ready4play.Peli;
import com.oh.ready4play.R;

import java.util.Random;


public class Hitler extends Fragment {
    ImageView ivKortti;
    TextView tvOhjeet;
    private TextView tvArvottu;
    private Random random = new Random();
    private int pelaajaVuorossa;
    public Hitler() {
        super(R.layout.fragment_hitler);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_hitler, container, false);

        pelaajaVuorossa = Peli.vuorossaPelaaja;
        tvArvottu = view.findViewById(R.id.tvArvottuPelaaja_Hitler);

        ivKortti = view.findViewById(R.id.ivKortti_Hitler);
        ivKortti.setImageResource(R.drawable.hampurilainen);
        tvOhjeet = view.findViewById(R.id.tvOhjeistus_Hitler);

        Button btJatkaPelia = view.findViewById(R.id.btJatkaPelia_Hitler);
        btJatkaPelia.setVisibility(View.INVISIBLE);

        ivKortti.setOnClickListener(e -> {
            int kortti = arvoKortti();
            kortinTapahtuma(kortti);
            btJatkaPelia.setVisibility(View.VISIBLE);
        });

        btJatkaPelia.setOnClickListener(e -> {
            tvArvottu.setText("");
            tvOhjeet.setText(R.string.text_taskDescriptionHitler);
            Peli.seuraavaVuoro = true;
            Peli.fragmentManager.beginTransaction()
                    .remove(this)
                    .setReorderingAllowed(true)
                    .commit();
        });
        return view;
    }

    //TODO: TEE KUVAT KORTEISTA JA ASETA NE TÄNNE!
    private void kortinTapahtuma(int kortti) {
        switch (kortti) {
            case 0 -> {
                //Laita tänne kuvat korteista
               ivKortti.setImageResource(R.drawable.noppa1);
               tvOhjeet.setText(R.string.text_waterfallDesc);
            }
            case 1 -> {
                ivKortti.setImageResource(R.drawable.noppa1);
                tvOhjeet.setText(R.string.text_give2);
            }
            case 3 -> {
                ivKortti.setImageResource(R.drawable.noppa1);
                tvOhjeet.setText(R.string.text_drink3);
            }
            case 4 -> {
                ivKortti.setImageResource(R.drawable.noppa1);
                tvOhjeet.setText(R.string.text_hitler);
            }
            case 5 -> {
                ivKortti.setImageResource(R.drawable.noppa1);
                tvOhjeet.setText(R.string.text_123);
            }
            case 6 -> {
                ivKortti.setImageResource(R.drawable.noppa1);
                tvOhjeet.setText(R.string.text_womenDrink);
            }
            case 7 -> {
                ivKortti.setImageResource(R.drawable.noppa1);
                tvOhjeet.setText(R.string.text_category);
            }
            case 8 -> {
                ivKortti.setImageResource(R.drawable.noppa1);
                tvOhjeet.setText(R.string.text_rule);
            }
            case 9 -> {
                ivKortti.setImageResource(R.drawable.noppa1);
                tvOhjeet.setText(R.string.text_everybodyDrink);
            }
            case 10 -> {
                ivKortti.setImageResource(R.drawable.noppa1);
                tvOhjeet.setText(R.string.text_menDrink);
            }
            case 11 -> {
                ivKortti.setImageResource(R.drawable.noppa1);
                tvOhjeet.setText(R.string.text_skip);
            }
            case 12 -> {
                ivKortti.setImageResource(R.drawable.noppa1);
                tvOhjeet.setText(R.string.text_hoe);
            }
            case 13 -> {
                ivKortti.setImageResource(R.drawable.noppa1);
                tvOhjeet.setText(R.string.text_kingdrink);
                int arvottu = pelaajaVuorossa;
                while (arvottu == pelaajaVuorossa) {
                    arvottu = random.nextInt(Peli.pelaajamaara);
                }
                tvArvottu.setText(Peli.pelaajat.get(arvottu).pelaajanimi);
            }
        }
    }

    private int arvoKortti() {
        return random.nextInt(14);
    }
}