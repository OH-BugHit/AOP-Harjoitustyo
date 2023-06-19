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
import com.oh.ready4play.MainActivity;
import com.oh.ready4play.Peli;
import com.oh.ready4play.R;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Ravit extends Fragment {
    private ArrayList<Kortti> ravipakka;
    private Random random = new Random();
    private ImageView ivPataAssa;
    private ImageView ivRistiAssa;
    private ImageView ivRuutuAssa;
    private ImageView ivHerttaAssa;
    private ImageView ivEkaTaso;
    private ImageView ivTokaTaso;
    private ImageView ivKolmasTaso;
    private ImageView ivNeljasTaso;
    private ImageView ivMaali;
    private ImageView ivArvottuKortti;
    private TextView tvOheistus;

    private boolean taso1 = false;
    private boolean taso2 = false;
    private boolean taso3 = false;
    private boolean taso4 = false;
    private boolean taso1Avattu = false;
    private boolean taso2Avattu = false;
    private boolean taso3Avattu = false;
    private boolean taso4Avattu = false;
    private int herttaSijainti = 0;
    private int pataSijainti = 0;
    private int ristiSijainti = 0;
    private int ruutuSijainti = 0;



    public Ravit() {super(R.layout.fragment_ravit);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ravit, container, false);

        tvOheistus = view.findViewById(R.id.tvOhjeistus_Ravit);

        Button btJatkaPelia = view.findViewById(R.id.btJatkaPelia_ravit);
        Button btAloita = view.findViewById(R.id.btAloita_Ravit);
        ivArvottuKortti = view.findViewById(R.id.ivArvottuKortti_Ravit);

        ivPataAssa = view.findViewById(R.id.ivPataAssa_Ravit);
        ivRistiAssa = view.findViewById(R.id.ivRistiAssa_Ravit);
        ivRuutuAssa = view.findViewById(R.id.ivRuutuAssa_Ravit);
        ivHerttaAssa = view.findViewById(R.id.ivHerttaAssa_Ravit);

        ivEkaTaso = view.findViewById(R.id.ivEkaTaso_Ravit);
        ivTokaTaso = view.findViewById(R.id.ivTokaTaso_Ravit);
        ivKolmasTaso = view.findViewById(R.id.ivKolmasTaso_Ravit);
        ivNeljasTaso = view.findViewById(R.id.ivNeljasTaso_Ravit);

        ivMaali = view.findViewById(R.id.ivMaali_Ravit);

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

    private void voittajaTarkistus() {
        if (herttaSijainti == 5) {
            ivArvottuKortti.setEnabled(false);
            tvOheistus.setText(R.string.text_heartsWinner);
            tvOheistus.setVisibility(View.VISIBLE);
        } else if (ruutuSijainti == 5) {
            ivArvottuKortti.setEnabled(false);
            tvOheistus.setText(R.string.text_DiamondsWinner);
            tvOheistus.setVisibility(View.VISIBLE);
        } else if (ristiSijainti == 5) {
            ivArvottuKortti.setEnabled(false);
            tvOheistus.setText(R.string.text_clubsWinner);
            tvOheistus.setVisibility(View.VISIBLE);
        } else if (pataSijainti == 5) {
            ivArvottuKortti.setEnabled(false);
            tvOheistus.setText(R.string.text_spadesWinner);
            tvOheistus.setVisibility(View.VISIBLE);
        }
    }

    private void alustaPeli() {
        ravipakka = (ArrayList<Kortti>) Peli.pakka.clone();
    }

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

    private Kortti nostaKortti() {
        return ravipakka.remove(random.nextInt(ravipakka.size()));
    }
}