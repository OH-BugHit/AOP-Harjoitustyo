package com.oh.ready4play.minipelit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.oh.ready4play.Kortti;
import com.oh.ready4play.Peli;
import com.oh.ready4play.R;

import java.util.ArrayList;
import java.util.Random;

public class Ravit extends Fragment {
    private ArrayList<Kortti> ravipakka;
    private Random random = new Random();
    private int taso1 = 0;
    private int taso2 = 0;
    private int taso3 = 0;
    private int taso4 = 0;
    private int herttaSijainti = 0;
    private int pataSijainti = 0;
    private int ristiSijainti = 0;
    private int ruutuSijainti = 0;



    public Ravit() {super(R.layout.fragment_ravit);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ravit, container, false);

        ravipakka = Peli.pakka;

        TextView tvOheistus = view.findViewById(R.id.tvOhjeistus_Ravit);

        Button btJatkaPelia = view.findViewById(R.id.btJatkaPelia_ravit);
        Button btAloita = view.findViewById(R.id.btAloita_Ravit);
        ImageView ivArvottuKortti = view.findViewById(R.id.ivArvottuKortti_Ravit);

        ImageView ivPataAssa = view.findViewById(R.id.ivPataAssa_Ravit);
        ImageView ivRistiAssa = view.findViewById(R.id.ivRistiAssa_Ravit);
        ImageView ivRuutuAssa = view.findViewById(R.id.ivRuutuAssa_Ravit);
        ImageView ivHerttaAssa = view.findViewById(R.id.ivHerttaAssa_Ravit);

        ImageView ivEkaTaso = view.findViewById(R.id.ivEkaTaso_Ravit);
        ImageView ivTokaTaso = view.findViewById(R.id.ivTokaTaso_Ravit);
        ImageView ivKolmasTaso = view.findViewById(R.id.ivKolmasTaso_Ravit);
        ImageView ivNeljasTaso = view.findViewById(R.id.ivNeljasTaso_Ravit);

        ImageView ivMaali = view.findViewById(R.id.ivMaali_Ravit);

        //Asetetaan piiloon kortit.
        ivArvottuKortti.setVisibility(View.INVISIBLE);

        ivHerttaAssa.setVisibility(View.INVISIBLE);
        ivPataAssa.setVisibility(View.INVISIBLE);
        ivRistiAssa.setVisibility(View.INVISIBLE);
        ivRuutuAssa.setVisibility(View.INVISIBLE);

        ivEkaTaso.setVisibility(View.INVISIBLE);
        ivKolmasTaso.setVisibility(View.INVISIBLE);
        ivTokaTaso.setVisibility(View.INVISIBLE);
        ivNeljasTaso.setVisibility(View.INVISIBLE);

        ivMaali.setVisibility(View.INVISIBLE);

        //TODO: ASETA OIKEAT KUVAT ÄSSIIN
        btAloita.setOnClickListener(e -> {
            ivHerttaAssa.setImageDrawable(ravipakka.remove(0).kuva);
            ivPataAssa.setImageDrawable(ravipakka.remove(13).kuva);
            ivRistiAssa.setImageDrawable(ravipakka.remove(26).kuva);
            ivRuutuAssa.setImageDrawable(ravipakka.remove(39).kuva);
            ivArvottuKortti.setImageResource(R.drawable.jokeri1);

            ivEkaTaso.setImageResource(R.drawable.jokeri1);
            ivTokaTaso.setImageDrawable(ravipakka.remove(random.nextInt(ravipakka.size())).kuva);
            ivKolmasTaso.setImageDrawable(ravipakka.remove(random.nextInt(ravipakka.size())).kuva);
            ivNeljasTaso.setImageDrawable(ravipakka.remove(random.nextInt(ravipakka.size())).kuva);

            ivHerttaAssa.setVisibility(View.VISIBLE);
            ivPataAssa.setVisibility(View.VISIBLE);
            ivRistiAssa.setVisibility(View.VISIBLE);
            ivRuutuAssa.setVisibility(View.VISIBLE);
            ivEkaTaso.setVisibility(View.VISIBLE);
            ivKolmasTaso.setVisibility(View.VISIBLE);
            ivTokaTaso.setVisibility(View.VISIBLE);
            ivNeljasTaso.setVisibility(View.VISIBLE);
            ivMaali.setVisibility(View.VISIBLE);

            btAloita.setVisibility(View.INVISIBLE);
            tvOheistus.setVisibility(View.INVISIBLE);
        });

        ivArvottuKortti.setOnClickListener(e -> {
            Kortti arvottuKortti = seuraavaKortti();
            suoritaVuoro(arvottuKortti.maa);
            ivArvottuKortti.setImageDrawable(arvottuKortti.kuva);
            if (taso1 == 4){
                Kortti tasoKortti = ravipakka.remove(random.nextInt(ravipakka.size()));
                ivEkaTaso.setImageDrawable(tasoKortti.kuva);
                peruutaMaata(tasoKortti.maa);
                taso1 = 0;
            } else if (taso2 == 4){
                Kortti tasoKortti = ravipakka.remove(random.nextInt(ravipakka.size()));
                ivTokaTaso.setImageDrawable(tasoKortti.kuva);
                peruutaMaata(tasoKortti.maa);
                taso2 = 0;
            } else if (taso3 == 4){
                Kortti tasoKortti = ravipakka.remove(random.nextInt(ravipakka.size()));
                ivKolmasTaso.setImageDrawable(tasoKortti.kuva);
                peruutaMaata(tasoKortti.maa);
                taso3 = 0;
            } else if (taso4 == 4){
                Kortti tasoKortti = ravipakka.remove(random.nextInt(ravipakka.size()));
                ivNeljasTaso.setImageDrawable(tasoKortti.kuva);
                peruutaMaata(tasoKortti.maa);
                taso4 = 0;
            }
        });

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