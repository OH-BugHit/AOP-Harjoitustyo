package com.oh.ready4play;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.Random;

/**
 * Pelaaja -olio sisältää pelaajan tiedot ja tilanteen
 * @version 1.0
 * @author Olli Hilke
 */
public class Pelaaja implements Serializable {
    /**
     * Indeksinumero nappulalle. 0-9
     */
    @Expose
    public int nappulaNumero;
    /**
     * Pelaajan nimi
     */
    @Expose
    public String pelaajanimi;
    /**
     * Pelinappulan kuva
     */
    public Drawable pelaajakuva;
    /**
     * Pelaajan sijainti pelilaudalla. Peliruudun numero. Default 0
     */
    @Expose
    public int sijainti = 0;
    /**
     * Pelaajan imageview
     */
    @Expose
    public ImageView imageView;
    /**
     * Kaksitotuutta tehtävän jälkeen tämä arvo asetetaan TRUE
     * Käytetään näyttämään tehtävän jälkeisten painikkeiden näyttämiseen
     */
    @Expose
    public boolean kaksiTotuutta = false;
    /**
     * Onko saatu bonusaskelia
     */
    @Expose
    public boolean bonusAskeleet = false;
    /**
     * Käytetään satunnaistamaan pelaajan sijainti peliruudussa.
     */
    @Expose
    private static final Random random = new Random();
    /**
     * Alustaja pelaajalle
     * @param pelaajaNimi Pelaajan nimi
     * @param pelaajaKuva Pelinappulan kuva
     * @param nappulaNumero Pelinappulan indeksinumero
     */
    public Pelaaja(int nappulaNumero, String pelaajaNimi, Drawable pelaajaKuva) {
        this.nappulaNumero = nappulaNumero;
        this.pelaajanimi = pelaajaNimi;
        this.pelaajakuva = pelaajaKuva;
    }
    /**
     * Pelaajan liikuttaminen ruutuun pelilaudalla
     * @param pelaaja Liikutettava pelaaja
     * @param uusiRuutu Ruutu, johon pelaaja liikutetaan
     */
    public static void  liikutaPelaajaRuutuun(Pelaaja pelaaja, Ruutu uusiRuutu) {
        pelaaja.sijainti = uusiRuutu.ruudunNumero;
        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        lp.width = 50;
        lp.height = 70;
        pelaaja.imageView.setLayoutParams(lp);
        if (uusiRuutu.pelaajiaRuudussa == 1) {
            pelaaja.imageView.setX(uusiRuutu.sijainti.x - 25);
            pelaaja.imageView.setY(uusiRuutu.sijainti.y - 70);
        } else {
            int arpa = random.nextInt(4);
            switch (arpa) {
                case 0 -> {
                    pelaaja.imageView.setX(uusiRuutu.sijainti.x - random.nextInt(18) - 6 - 25);
                    pelaaja.imageView.setY(uusiRuutu.sijainti.y - random.nextInt(18) - 6 - 70);
                }
                case 1 -> {
                    pelaaja.imageView.setX(uusiRuutu.sijainti.x - random.nextInt(18) - 6 - 25);
                    pelaaja.imageView.setY(uusiRuutu.sijainti.y + random.nextInt(18) + 6 - 70);
                }
                case 2 -> {
                    pelaaja.imageView.setX(uusiRuutu.sijainti.x + random.nextInt(18) + 6 - 25);
                    pelaaja.imageView.setY(uusiRuutu.sijainti.y - random.nextInt(18) - 6 - 70);
                }
                case 3 -> {
                    pelaaja.imageView.setX(uusiRuutu.sijainti.x + random.nextInt(18) + 6 - 25);
                    pelaaja.imageView.setY(uusiRuutu.sijainti.y + random.nextInt(18) + 6 - 70);
                }
            }
        }
    }
}
