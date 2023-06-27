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
import java.util.Random;


public class Bussikuski extends Fragment {
    private final Random random = new Random();
    private boolean onnistui;
    private int edellinenArvo;
    private int uusiArvo;
    private int tasoMenossa = 1;
    private int sakko = 0;
    private ArrayList<Kortti> bussiPakka;
    private ImageView kortti4;
    private ImageView kortti1;
    private ImageView kortti5;
    private ImageView kortti2;
    private ImageView kortti3;
    private ImageView osoitin;
    private ImageView ivAloituskortti;
    private TextView tvOheistus;
    private TextView tvAloituskortti;
    private TextView tvSakko;
    private TextView tvSakkoMaara;
    private Button btYlempi;
    private Button btAlempi;
    private Button btJatkaPelia;


    public Bussikuski() {super(R.layout.fragment_bussikuski);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bussikuski, container, false);

        alustaKorttipakka();

        tvSakkoMaara = view.findViewById(R.id.tvSakkoMaara_Bussikuski);
        tvSakko = view.findViewById(R.id.tvSakko_Bussikuski);
        tvAloituskortti = view.findViewById(R.id.tvAloituskortti_Bussikuski);
        tvOheistus = view.findViewById(R.id.tvOhjeistus_Bussikuski);
        btJatkaPelia = view.findViewById(R.id.btJatkaPelia_Bussikuski);
        Button btAloita = view.findViewById(R.id.btAloita_Bussikuski);
        btYlempi = view.findViewById(R.id.btSuurempi_Bussikuski);
        btAlempi = view.findViewById(R.id.btPienempi_Bussikuski);

        ivAloituskortti = view.findViewById(R.id.ivAloituskortti_Bussikuski);
        kortti1 = view.findViewById(R.id.ivKortti1_Bussikuski);
        kortti2 = view.findViewById(R.id.ivKortti2_Bussikuski);
        kortti3 = view.findViewById(R.id.ivKortti3_Bussikuski);
        kortti4 = view.findViewById(R.id.ivKortti4_Bussikuski);
        kortti5 = view.findViewById(R.id.ivKortti5_Bussikuski);
        osoitin = view.findViewById(R.id.ivOsoitin_Bussikuski);
        osoitin.setImageResource(R.drawable.kysymysmerkkimusta35);

        ivAloituskortti.setVisibility(View.INVISIBLE);
        tvAloituskortti.setVisibility(View.INVISIBLE);
        kortti4.setVisibility(View.INVISIBLE);
        kortti1.setVisibility(View.INVISIBLE);
        kortti5.setVisibility(View.INVISIBLE);
        kortti2.setVisibility(View.INVISIBLE);
        kortti3.setVisibility(View.INVISIBLE);
        kortti4.setVisibility(View.INVISIBLE);
        kortti1.setVisibility(View.INVISIBLE);
        kortti5.setVisibility(View.INVISIBLE);
        kortti2.setVisibility(View.INVISIBLE);
        kortti3.setVisibility(View.INVISIBLE);
        osoitin.setVisibility(View.INVISIBLE);
        btJatkaPelia.setVisibility(View.INVISIBLE);
        btYlempi.setVisibility(View.INVISIBLE);
        btAlempi.setVisibility(View.INVISIBLE);
        kortti1.setImageResource(R.drawable.tausta);
        kortti2.setImageResource(R.drawable.tausta);
        kortti3.setImageResource(R.drawable.tausta);
        kortti4.setImageResource(R.drawable.tausta);
        kortti5.setImageResource(R.drawable.tausta);
        ivAloituskortti.setImageResource(R.drawable.tausta);


        btAloita.setOnClickListener(e -> {
            peliNakyviin();
            btAloita.setVisibility(View.INVISIBLE);
            btJatkaPelia.setVisibility(View.INVISIBLE);
            ivAloituskortti.setVisibility(View.VISIBLE);
            osoitin.setVisibility(View.VISIBLE);
            asetaAloituskortti();
        });

        btAlempi.setOnClickListener(e -> {
            btAlempi.setEnabled(false);
            btYlempi.setEnabled(false);
            onnistui = suoritaVuoro(0);
            vuoronJalkeen();
        });
        btYlempi.setOnClickListener(e -> {
            btAlempi.setEnabled(false);
            btYlempi.setEnabled(false);
            onnistui = suoritaVuoro(1);
            vuoronJalkeen();
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

    private void vuoronJalkeen() {
        btAlempi.setEnabled(true);
        btYlempi.setEnabled(true);
        edellinenArvo = uusiArvo;
        siirraOsoitinta(onnistui);
        if (tasoMenossa > 5 || sakko > 3) {
            osoitin.setVisibility(View.INVISIBLE);
            btYlempi.setVisibility(View.INVISIBLE);
            btAlempi.setVisibility(View.INVISIBLE);
            btJatkaPelia.setVisibility(View.VISIBLE);
            tvOheistus.setVisibility(View.VISIBLE);
            if (sakko > 3) {
                tvOheistus.setText(R.string.text_busDriverFailEnd);
                Peli.tehtavaFail = true;
            } else {
                tvOheistus.setText(R.string.text_busDriverEnd);
            }
            tvSakko.setText(R.string.text_penaltyBusdriver);
            sakko *= 3;
            tvSakkoMaara.setText(String.valueOf(sakko));
        }
    }

    private void siirraOsoitinta(boolean onnistui) {
        if (!onnistui) {
            osoitin.setX((float) (kortti1.getX()+kortti1.getHeight()*0.5));
            osoitin.setY(kortti1.getY());
        } else if (tasoMenossa == 2) {
            osoitin.setX((float) (kortti2.getX()+kortti2.getHeight()*0.5));
            osoitin.setY(kortti2.getY());
        } else if (tasoMenossa == 3) {
            osoitin.setX((float) (kortti3.getX()+kortti3.getHeight()*0.5));
            osoitin.setY(kortti3.getY());
        } else if (tasoMenossa == 4) {
            osoitin.setX((float) (kortti4.getX()+kortti4.getHeight()*0.5));
            osoitin.setY(kortti4.getY());
        } else if (tasoMenossa == 5) {
            osoitin.setX((float) (kortti5.getX()+kortti5.getHeight()*0.5));
            osoitin.setY(kortti5.getY());
        }
    }

    /**
     * Suorittaa vuoron
     * @param low0hi1 0 jos arvauksena alempi, 1 jos ylempi.
     */
    private boolean suoritaVuoro(int low0hi1) {
        Kortti nosto = nostaKortti();
        System.out.println(nosto.arvo);
        uusiArvo = nosto.arvo;
        if (tasoMenossa == 1) {
            kortti1.setImageDrawable(nosto.kuva);
            return testaaMenikoOikein(low0hi1, nosto);
        } else if (tasoMenossa == 2) {
            kortti2.setImageDrawable(nosto.kuva);
            return testaaMenikoOikein(low0hi1, nosto);
        } else if (tasoMenossa == 3) {
            kortti3.setImageDrawable(nosto.kuva);
            return testaaMenikoOikein(low0hi1, nosto);
        } else if (tasoMenossa == 4) {
            kortti4.setImageDrawable(nosto.kuva);
            return testaaMenikoOikein(low0hi1, nosto);
        } else {
            kortti5.setImageDrawable(nosto.kuva);
            return testaaMenikoOikein(low0hi1, nosto);
        }
    }

    private boolean testaaMenikoOikein(int low0hi1, Kortti nosto) {
        if (low0hi1 == 0) {
            if (nosto.arvo < edellinenArvo) {
                tasoMenossa ++;
                return true;
            } else {
                Thread t1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(600);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        MainActivity.INSTANCE.runOnUiThread(Bussikuski.this::piilotaKortit);
                    }
                });
                t1.start();
                //piilotaKortit();
                sakko ++;
                tvSakko.setText(R.string.text_falseGuesses_Busdriver);
                tvSakkoMaara.setText(String.valueOf(sakko));
                tasoMenossa = 1;
                return false;
            }
        } else {
            if (nosto.arvo > edellinenArvo) {
                tasoMenossa ++;
                return true;
            } else {
                Thread t1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(600);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        MainActivity.INSTANCE.runOnUiThread(Bussikuski.this::piilotaKortit);
                    }
                });
                t1.start();
                //piilotaKortit();
                sakko ++;
                tvSakko.setText(R.string.text_falseGuesses_Busdriver);
                tvSakkoMaara.setText(String.valueOf(sakko));
                tasoMenossa = 1;
                return false;
            }
        }
    }

    private void piilotaKortit() {
        kortti1.setImageResource(R.drawable.tausta);
        kortti2.setImageResource(R.drawable.tausta);
        kortti3.setImageResource(R.drawable.tausta);
        kortti4.setImageResource(R.drawable.tausta);
        kortti5.setImageResource(R.drawable.tausta);
    }

    private void asetaAloituskortti() {
        Kortti nosto = nostaKortti();
        System.out.println("Aloituskortti: " + nosto.arvo);
        edellinenArvo = nosto.arvo;
        ivAloituskortti.setImageDrawable(nosto.kuva);
    }

    private Kortti nostaKortti() {
        Kortti palautus = bussiPakka.remove(random.nextInt(bussiPakka.size()));
        return palautus;
    }

    private void alustaKorttipakka() {
        bussiPakka = (ArrayList<Kortti>) Peli.pakka.clone();
    }

    private void peliNakyviin() {
        tvOheistus.setVisibility(View.INVISIBLE);
        tvAloituskortti.setVisibility(View.VISIBLE);
        kortti4.setVisibility(View.VISIBLE);
        kortti1.setVisibility(View.VISIBLE);
        kortti5.setVisibility(View.VISIBLE);
        kortti2.setVisibility(View.VISIBLE);
        kortti3.setVisibility(View.VISIBLE);
        osoitin.setVisibility(View.VISIBLE);
        btYlempi.setVisibility(View.VISIBLE);
        btAlempi.setVisibility(View.VISIBLE);
    }
}