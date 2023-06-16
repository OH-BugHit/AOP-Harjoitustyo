package com.oh.ready4play;

import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * Pelaaja -olio sisältää pelaajan tiedot ja tilanteen
 */
public class Pelaaja {
    /**
     * Indeksinumero nappulalle. 0-9
     */
    public int nappulaNumero;
    /**
     * Pelaajan nimi
     */
    public String pelaajanimi;
    /**
     * Pelinappulan kuva
     */
    public Drawable pelaajakuva;

    /**
     * Pelaajan sijainti pelilaudalla. Peliruudun numero
     */
    public int sijainti;

    public ImageView imageView;

    public boolean kaksiTotuutta = false;
    public boolean bonusAskeleet = false;

    /**
     * Alustaja pelaajalle
     * @param pelaajaNimi Pelaajan nimi
     * @param pelaajaKuva Pelinappulan kuva
     */
    public Pelaaja(int nappulaNumero, String pelaajaNimi, Drawable pelaajaKuva) {
        this.nappulaNumero = nappulaNumero;
        this.pelaajanimi = pelaajaNimi;
        this.pelaajakuva = pelaajaKuva;
    }

    public static void liikutaPelaajaRuutuun(Pelaaja pelaaja, Ruutu uusiRuutu) {
        pelaaja.sijainti = uusiRuutu.ruudunNumero;
        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        lp.width = 25;
        lp.height = 30;
        pelaaja.imageView.setLayoutParams(lp);
        pelaaja.imageView.setX(uusiRuutu.sijainti.x);
        pelaaja.imageView.setY(uusiRuutu.sijainti.y);
        //uusiRuutu.sijainti.x);
        //pelaaja.imageView.setY(uusiRuutu.sijainti.y);
    }
}
