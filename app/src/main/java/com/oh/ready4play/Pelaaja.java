package com.oh.ready4play;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

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
        pelaaja.imageView.setX(uusiRuutu.sijainti.x);
        pelaaja.imageView.setY(uusiRuutu.sijainti.y);
    }
}
