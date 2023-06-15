package com.oh.ready4play;

public class Ruutu {
    /**
     * Ruutu-luokan alustaja. Alustaja asettaa lisasiirrot = 0 ja pelaajiaRuudussa = 0
     */
    public Ruutu() {
        this.lisaSiirrot = 0;
        this.pelaajiaRuudussa = 0;
    }

    /**
     * Peliruudun yksilöivä numero.
     */
    protected int ruudunNumero;
    /**
     * Peliruudun x/y sijainti. Suhteellinen sijainti
     */
    protected Sijainti sijainti;

    /**
     * Ruudun tehtävänumero
     * 0 = Lähtö
     * 1 = Hitler
     * 2 = Huora
     * 3 = Tytöt vs. pojat
     * 4 = Totuus ja tehtävä
     * 5 = Would you rather
     * 6 = 3 shottia
     * 7 = 2 totuutta ja 1 valhe
     * 8 = Sanaselitys
     * 9 = Fuck the dealer
     * 10 = Ravit
     * 11 = Bussikuskin vika taso
     * 12 = Kasa
     * 13 = Maali
     */
    protected int tehtava;
    /**
     * Lisäsiirrot tehtävän onnistuessa tai epäonnistuessa.
     */
    protected int lisaSiirrot;

    protected int pelaajiaRuudussa;
}
