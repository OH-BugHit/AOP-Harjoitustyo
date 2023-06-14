package com.oh.ready4play;

import android.graphics.drawable.Drawable;

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
     * Alustaja pelaajalle
     * @param pelaajaNimi Pelaajan nimi
     * @param pelaajaKuva Pelinappulan kuva
     */

    public Pelaaja(int nappulaNumero, String pelaajaNimi, Drawable pelaajaKuva) {
        this.nappulaNumero = nappulaNumero;
        this.pelaajanimi = pelaajaNimi;
        this.pelaajakuva = pelaajaKuva;
    }
}
