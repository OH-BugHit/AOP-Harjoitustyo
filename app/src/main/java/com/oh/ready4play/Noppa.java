package com.oh.ready4play;

import android.view.View;
import android.widget.ImageView;

import java.util.Random;

public class Noppa {
    private static final Random random = new Random();
    private final ImageView noppaKuva;
    private int heitto;

    public Noppa(ImageView noppaKuva) {
        this.noppaKuva = noppaKuva;
    }

    /**
     * Heittää nopan ja asettaa nopan kuvan näkyviin
     * @return Palauttaa heiton arvon (mukaan laskettu asetuksissa määritelty asetus)
     */
    public int heitaNoppaa() throws InterruptedException {
        noppaKuva.setVisibility(View.VISIBLE);

        int animaatioKesto = random.nextInt(19) + 6;
        int animaatioNopeus = random.nextInt(50) + 30;

        for (int i = animaatioKesto; i > 0; i--) {
            heitto = random.nextInt(6) + 1 + Peli.peliasetukset.noppa;
                Thread.sleep(animaatioNopeus);
            animaatioNopeus += random.nextInt(10);
            naytaHeittokuva(heitto);
        }

        Thread.sleep(1000);


        if (Peli.pelaajat.get(Peli.vuorossaPelaaja).bonusAskeleet) {
            heitto += 3;
            Peli.pelaajat.get(Peli.vuorossaPelaaja).bonusAskeleet = false;
        }
        if (Peli.pelaajat.get(Peli.vuorossaPelaaja).sijainti + heitto > 61) {
            heitto = 61 - Peli.pelaajat.get(Peli.vuorossaPelaaja).sijainti;
        }
        return heitto ;
    }

    /**
     * Asettaa heiton mukaisen noppakuvan
     * @param heitto nopan heiton tulos
     */
    private void naytaHeittokuva(int heitto) {
        switch (heitto) {
            case 1 -> noppaKuva.setImageResource(R.drawable.noppa1);
            case 2 -> noppaKuva.setImageResource(R.drawable.noppa2);
            case 3 -> noppaKuva.setImageResource(R.drawable.noppa3);
            case 4 -> noppaKuva.setImageResource(R.drawable.noppa4);
            case 5 -> noppaKuva.setImageResource(R.drawable.noppa5);
            case 6 -> noppaKuva.setImageResource(R.drawable.noppa6);
            case 7 -> noppaKuva.setImageResource(R.drawable.noppa7);
            case 8 -> noppaKuva.setImageResource(R.drawable.noppa8);
            case 9 -> noppaKuva.setImageResource(R.drawable.noppa9);
            case 10 -> noppaKuva.setImageResource(R.drawable.noppa10);
            case 11 -> noppaKuva.setImageResource(R.drawable.noppa11);
        }
    }
}