package com.oh.ready4play;

/**
 * Luokka sisältää tallennettavat ja ladattavat peliasetukset joita käytetään pelissä.
 * @version 1.0
 * @author Olli Hilke
 */
public class Peliasetukset {
    /**
     * Nopan bonussiirrot
     */
    int noppa = 0;
    /**
     * Sanaselityspelin kesto
     */
    public int sanaselitysKesto = 45;
    /**
     * True jos Hitler tehtävä on käytössä, muuten false
     */
    boolean hitler = true;
    /**
     * True jos Huora tehtävä on käytössä, muuten false
     */
    boolean huora = true;
    /**
     * True jos TytötVsPojat tehtävä on käytössä, muuten false
     */
    boolean tytotVsPojat = true;
    /**
     * True jos TotuusVaiTehtävä tehtävä on käytössä, muuten false
     */
    boolean totuusVaiTehtava = true;
    /**
     * True jos Would You Rather tehtävä on käytössä, muuten false
     */
    boolean wouldYouRather = true;
    /**
     * True jos Kolme Shottia tehtävä on käytössä, muuten false
     */
    boolean kolmeShottia = true;
    /**
     * True jos Kaksi Totuutta Yksi Valhe tehtävä on käytössä, muuten false
     */
    boolean kaksiTotuutta1Valhe = true;
    /**
     * True jos Sanaselitys tehtävä on käytössä, muuten false
     */
    boolean sanaselitys = true;
    /**
     * True jos Fuck The Dealer tehtävä on käytössä, muuten false
     */
    boolean fuckTheDealer = true;
    /**
     * True jos Ravit tehtävä on käytössä, muuten false
     */
    boolean ravit = true;
    /**
     * True jos Bussikuski tehtävä on käytössä, muuten false
     */
    boolean bussikuski = true;
    /**
     * True jos Kasa tehtävä on käytössä, muuten false
     */
    boolean kasa = true;

    /**
     * Metodi tarkistaa, onko kaikki pelin tehtävät käytössä
     * @return Palauttaa True, mikäli kaikki pelin tehtävät ovat käytössä, muuten False
     */
    public boolean kaikkitrue(){
        return this.hitler && this.huora && this.tytotVsPojat && this.totuusVaiTehtava && this.wouldYouRather && this.kolmeShottia && this.kaksiTotuutta1Valhe && this.sanaselitys && this.fuckTheDealer && this.ravit && this.bussikuski && this.kasa;
    }

    /**
     * Tyhjä alustaja
     */
    public Peliasetukset () {
    }

    /*
    public int falseLukumaara() {
        int falseLkm = 0;
        if (!this.hitler) {falseLkm ++;}
        if (!this.huora) {falseLkm ++;}
        if (!this.tytotVsPojat) {falseLkm ++;}
        if (!this.totuusVaiTehtava) {falseLkm ++;}
        if (!this.wouldYouRather) {falseLkm ++;}
        if (!this.kolmeShottia) {falseLkm ++;}
        if (!this.kaksiTotuutta1Valhe) {falseLkm ++;}
        if (!this.sanaselitys) {falseLkm ++;}
        if (!this.fuckTheDealer) {falseLkm ++;}
        if (!this.ravit) {falseLkm ++;}
        if (!this.bussikuski) {falseLkm ++;}
        if (!this.kasa) {falseLkm ++;}
        return falseLkm;
    }
     */
}
