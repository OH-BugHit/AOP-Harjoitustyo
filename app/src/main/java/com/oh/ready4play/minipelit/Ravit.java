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
import java.util.Objects;
import java.util.Random;

public class Ravit extends Fragment {
    /**
     * Ravit-peli käyttää normaalia 52 kortin korttipakkaa joka koostuu korteista.
     */
    private ArrayList<Kortti> ravipakka;
    private final Random random = new Random();
    /**
     * Nappula pelin jatkamiseksi
     */
    private Button btJatkaPelia;
    /**
     * Pataässän ImageView
     */
    private ImageView ivPataAssa;
    /**
     * Ristiässän ImageView
     */
    private ImageView ivRistiAssa;
    /**
     * Ruutuässän ImageView
     */
    private ImageView ivRuutuAssa;
    /**
     * Herttaässän ImageView
     */
    private ImageView ivHerttaAssa;
    /**
     * Ensimmäisen tason sivukortin ImageView
     */
    private ImageView ivEkaTaso;
    /**
     * Toisen tason sivukortin ImageView
     */
    private ImageView ivTokaTaso;
    /**
     * Kolmannen tason sivukortin ImageView
     */
    private ImageView ivKolmasTaso;
    /**
     * Neljännen tason sivukortin ImageView
     */
    private ImageView ivNeljasTaso;
    /**
     * Maaliviivan ImageView
     */
    private ImageView ivMaali;
    /**
     * Arvotun kortin ImageView
     */
    private ImageView ivArvottuKortti;
    private ImageView ivTausta;
    /**
     * Ohjeiden ja lopputekstin TextView
     */
    private TextView tvTitle;
    private TextView tvOheistus;
    /**
     * Käytetään määrittämään onko taso 1 saavutettu kaikilla ässillä
     */
    private boolean taso1 = false;
    /**
     * Käytetään määrittämään onko taso 2 saavutettu kaikilla ässillä
     */
    private boolean taso2 = false;
    /**
     * Käytetään määrittämään onko taso 3 saavutettu kaikilla ässillä
     */
    private boolean taso3 = false;
    /**
     * Käytetään määrittämään onko taso 4 saavutettu kaikilla ässillä
     */
    private boolean taso4 = false;
    /**
     * Käytetään määrittämään onko tason 1 kortti käännetty
     */
    private boolean taso1Avattu = false;
    /**
     * Käytetään määrittämään onko tason 2 kortti käännetty
     */
    private boolean taso2Avattu = false;
    /**
     * Käytetään määrittämään onko tason 3 kortti käännetty
     */
    private boolean taso3Avattu = false;
    /**
     * Käytetään määrittämään onko tason 4 kortti käännetty
     */
    private boolean taso4Avattu = false;
    /**
     * Hertta ässän taso
     */
    private int herttaSijainti = 0;
    /**
     * Pataässän taso
     */
    private int pataSijainti = 0;
    /**
     * Ristiässän taso
     */
    private int ristiSijainti = 0;
    /**
     * Ruutuässän taso
     */
    private int ruutuSijainti = 0;

    public Ravit() {super(R.layout.fragment_ravit);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ravit, container, false);

        tvOheistus = view.findViewById(R.id.tvOhjeistus_Ravit);
        tvTitle = view.findViewById(R.id.tvTitle_Ravit);

        btJatkaPelia = view.findViewById(R.id.btJatkaPelia_ravit);
        //Piilotetaan pelin jatkamisnäppäin pelin ajaksi. Se tulee takaisin nähtäville kun peli ohitse
        btJatkaPelia.setVisibility(View.INVISIBLE);
        Button btAloita = view.findViewById(R.id.btAloita_Ravit);
        ivArvottuKortti = view.findViewById(R.id.ivArvottuKortti_Ravit);
        ivTausta = view.findViewById(R.id.ivTausta_Ravit);
        ivTausta.setVisibility(View.INVISIBLE);

        ivPataAssa = view.findViewById(R.id.ivPataAssa_Ravit);
        ivRistiAssa = view.findViewById(R.id.ivRistiAssa_Ravit);
        ivRuutuAssa = view.findViewById(R.id.ivRuutuAssa_Ravit);
        ivHerttaAssa = view.findViewById(R.id.ivHerttaAssa_Ravit);

        ivEkaTaso = view.findViewById(R.id.ivEkaTaso_Ravit);
        ivTokaTaso = view.findViewById(R.id.ivTokaTaso_Ravit);
        ivKolmasTaso = view.findViewById(R.id.ivKolmasTaso_Ravit);
        ivNeljasTaso = view.findViewById(R.id.ivNeljasTaso_Ravit);

        ivMaali = view.findViewById(R.id.ivMaali_Ravit);
        ivMaali.setImageResource(R.drawable.maaliviiva);

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

        //Myös maali piiloon
        ivMaali.setVisibility(View.INVISIBLE);

        btAloita.setOnClickListener(e -> {
            tvTitle.setVisibility(View.INVISIBLE);
            ivTausta.setVisibility(View.VISIBLE);
            alustaPeli();
            ivHerttaAssa.setImageDrawable(ravipakka.remove(0).kuva);
            ivRuutuAssa.setImageDrawable(ravipakka.remove(12).kuva);
            ivPataAssa.setImageDrawable(ravipakka.remove(24).kuva);
            ivRistiAssa.setImageDrawable(ravipakka.remove(36).kuva);
            ivArvottuKortti.setImageResource(R.drawable.tausta);

            ivEkaTaso.setImageResource(R.drawable.tausta);
            ivTokaTaso.setImageResource(R.drawable.tausta);
            ivKolmasTaso.setImageResource(R.drawable.tausta);
            ivNeljasTaso.setImageResource(R.drawable.tausta);

            ivArvottuKortti.setVisibility(View.VISIBLE);
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
            Kortti tasoKortti;
            Kortti arvottuKortti = nostaKortti();
            liikutaMaata(arvottuKortti.maa,true);
            ivArvottuKortti.setImageDrawable(arvottuKortti.kuva);
            tasojenTilanne();
            if (taso1){
                tasoKortti = nostaKortti();
                ivEkaTaso.setImageDrawable(tasoKortti.kuva);
                liikutaMaata(tasoKortti.maa,false);
                taso1 = false;
                taso1Avattu = true;
            } else if (taso2){
                tasoKortti = nostaKortti();
                ivTokaTaso.setImageDrawable(tasoKortti.kuva);
                liikutaMaata(tasoKortti.maa,false);
                taso2 = false;
                taso2Avattu = true;
            } else if (taso3){
                tasoKortti = nostaKortti();
                ivKolmasTaso.setImageDrawable(tasoKortti.kuva);
                liikutaMaata(tasoKortti.maa,false);
                taso3 = false;
                taso3Avattu = true;
            } else if (taso4){
                tasoKortti = nostaKortti();
                ivNeljasTaso.setImageDrawable(tasoKortti.kuva);
                liikutaMaata(tasoKortti.maa,false);
                taso4 = false;
                taso4Avattu = true;
            }
            voittajaTarkistus();
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

    /**
     * Tarkistetaan onko kukaan ylittänyt maaliviivaa
     */
    private void voittajaTarkistus() {
        if (herttaSijainti == 5) {
            ivArvottuKortti.setEnabled(false);
            tvOheistus.setText(R.string.text_heartsWinner);
            tvOheistus.setVisibility(View.VISIBLE);
            btJatkaPelia.setVisibility(View.VISIBLE);
        } else if (ruutuSijainti == 5) {
            ivArvottuKortti.setEnabled(false);
            tvOheistus.setText(R.string.text_diamondsWinner);
            tvOheistus.setVisibility(View.VISIBLE);
            btJatkaPelia.setVisibility(View.VISIBLE);
        } else if (ristiSijainti == 5) {
            ivArvottuKortti.setEnabled(false);
            tvOheistus.setText(R.string.text_clubsWinner);
            tvOheistus.setVisibility(View.VISIBLE);
            btJatkaPelia.setVisibility(View.VISIBLE);
        } else if (pataSijainti == 5) {
            ivArvottuKortti.setEnabled(false);
            tvOheistus.setText(R.string.text_spadesWinner);
            tvOheistus.setVisibility(View.VISIBLE);
            btJatkaPelia.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Kopiodaan Peli-luokasta korttipakka
     */
    private void alustaPeli() {
        ravipakka = (ArrayList<Kortti>) Peli.pakka.clone();
    }

    /**
     * Tarkistetaan ovatko kaikki ässät ohittaneet jonkin tason
     */
    private void tasojenTilanne() {
        if (herttaSijainti > 0 && ruutuSijainti > 0 && pataSijainti > 0 && ristiSijainti > 0 && !taso1Avattu) {
            taso1 = true;
        }
        if (herttaSijainti > 1 && ruutuSijainti > 1 && pataSijainti > 1 && ristiSijainti > 1 && !taso2Avattu) {
            taso2 = true;
        }
        if (herttaSijainti > 2 && ruutuSijainti > 2 && pataSijainti > 2 && ristiSijainti > 2 && !taso3Avattu) {
            taso3 = true;
        }
        if (herttaSijainti > 3 && ruutuSijainti > 3 && pataSijainti > 3 && ristiSijainti > 3 && !taso4Avattu) {
            taso4 = true;
        }
    }

    /**
     * Liikuttaa ässää.
     * @param maa Liikutettava ässä
     * @param suunta liikutettava suunta.
     *               TRUE = Eteenpäin, FALSE = Taaksepäin
     */
    private void liikutaMaata(String maa, boolean suunta) {
        if (Objects.equals(maa, "Hertta")) {
            switch (herttaSijainti) {
                case 0 -> {
                    if (suunta) {
                        ivHerttaAssa.setX(ivEkaTaso.getX());
                        herttaSijainti ++;
                    }
                }
                case 1 -> {
                    if (suunta) {
                        ivHerttaAssa.setX(ivTokaTaso.getX());
                        herttaSijainti ++;
                    } else {
                        ivHerttaAssa.setX(ivArvottuKortti.getX());
                        herttaSijainti --;
                    }
                }
                case 2 -> {
                    if (suunta) {
                        ivHerttaAssa.setX(ivKolmasTaso.getX());
                        herttaSijainti ++;
                    } else {
                        ivHerttaAssa.setX(ivEkaTaso.getX());
                        herttaSijainti --;
                    }
                }
                case 3 -> {
                    if (suunta) {
                        ivHerttaAssa.setX(ivNeljasTaso.getX());
                        herttaSijainti ++;
                    } else {
                        ivHerttaAssa.setX(ivTokaTaso.getX());
                        herttaSijainti --;
                    }
                }
                case 4 -> {
                    if (suunta) {
                        ivHerttaAssa.setX(ivMaali.getX());
                        herttaSijainti ++;
                    } else {
                        ivHerttaAssa.setX(ivKolmasTaso.getX());
                        herttaSijainti --;
                    }
                }
            }
        } else if (Objects.equals(maa, "Ruutu")) {
            switch (ruutuSijainti) {
                case 0 -> {
                    if (suunta) {
                        ivRuutuAssa.setX(ivEkaTaso.getX());
                        ruutuSijainti ++;
                    }
                }
                case 1 -> {
                    if (suunta) {
                        ivRuutuAssa.setX(ivTokaTaso.getX());
                        ruutuSijainti ++;
                    } else {
                        ivRuutuAssa.setX(ivArvottuKortti.getX());
                        ruutuSijainti --;
                    }
                }
                case 2 -> {
                    if (suunta) {
                        ivRuutuAssa.setX(ivKolmasTaso.getX());
                        ruutuSijainti ++;
                    } else {
                        ivRuutuAssa.setX(ivEkaTaso.getX());
                        ruutuSijainti --;
                    }
                }
                case 3 -> {
                    if (suunta) {
                        ivRuutuAssa.setX(ivNeljasTaso.getX());
                        ruutuSijainti ++;
                    } else {
                        ivRuutuAssa.setX(ivTokaTaso.getX());
                        ruutuSijainti --;
                    }
                }
                case 4 -> {
                    if (suunta) {
                        ivRuutuAssa.setX(ivMaali.getX());
                        ruutuSijainti ++;
                    } else {
                        ivRuutuAssa.setX(ivKolmasTaso.getX());
                        ruutuSijainti --;
                    }
                }
            }
        } else if (Objects.equals(maa, "Risti")) {
            switch (ristiSijainti)      {
                case 0 -> {
                    if (suunta) {
                        ivRistiAssa.setX(ivEkaTaso.getX());
                        ristiSijainti ++;
                    }
                }
                case 1 -> {
                    if (suunta) {
                        ivRistiAssa.setX(ivTokaTaso.getX());
                        ristiSijainti ++;
                    } else {
                        ivRistiAssa.setX(ivArvottuKortti.getX());
                        ristiSijainti --;
                    }
                }
                case 2 -> {
                    if (suunta) {
                        ivRistiAssa.setX(ivKolmasTaso.getX());
                        ristiSijainti ++;
                    } else {
                        ivRistiAssa.setX(ivEkaTaso.getX());
                        ristiSijainti --;
                    }
                }
                case 3 -> {
                    if (suunta) {
                        ivRistiAssa.setX(ivNeljasTaso.getX());
                        ristiSijainti ++;
                    } else {
                        ivRistiAssa.setX(ivTokaTaso.getX());
                        ristiSijainti --;
                    }
                }
                case 4 -> {
                    if (suunta) {
                        ivRistiAssa.setX(ivMaali.getX());
                        ristiSijainti ++;
                    } else {
                        ivRistiAssa.setX(ivKolmasTaso.getX());
                        ristiSijainti --;
                    }
                }
            }
        } else if (Objects.equals(maa, "Pata")) {
            switch (pataSijainti) {
                case 0 -> {
                    if (suunta) {
                        ivPataAssa.setX(ivEkaTaso.getX());
                        pataSijainti ++;
                    }
                }
                case 1 -> {
                    if (suunta) {
                        ivPataAssa.setX(ivTokaTaso.getX());
                        pataSijainti ++;
                    } else {
                        ivPataAssa.setX(ivArvottuKortti.getX());
                        pataSijainti --;
                    }
                }
                case 2 -> {
                    if (suunta) {
                        ivPataAssa.setX(ivKolmasTaso.getX());
                        pataSijainti ++;
                    } else {
                        ivPataAssa.setX(ivEkaTaso.getX());
                        pataSijainti --;
                    }
                }
                case 3 -> {
                    if (suunta) {
                        ivPataAssa.setX(ivNeljasTaso.getX());
                        pataSijainti ++;
                    } else {
                        ivPataAssa.setX(ivTokaTaso.getX());
                        pataSijainti --;
                    }
                }
                case 4 -> {
                    if (suunta) {
                        ivPataAssa.setX(ivMaali.getX());
                        pataSijainti ++;
                    } else {
                        ivPataAssa.setX(ivKolmasTaso.getX());
                        pataSijainti --;
                    }
                }
            }
        }
    }

    /**
     * Arvotaan kortti ja poistetaan se pakasta (Eli nostetaan)
     * @return Palauttaa nostetun kortin
     */
    private Kortti nostaKortti() {
        return ravipakka.remove(random.nextInt(ravipakka.size()));
    }
}