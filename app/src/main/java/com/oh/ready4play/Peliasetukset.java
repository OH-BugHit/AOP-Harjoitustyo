package com.oh.ready4play;

/**
 * Luokka sisältää tallennettavat ja ladattavat peliasetukset joita käytetään pelissä.
 */
public class Peliasetukset {
    /**
     * Nopan bonussiirrot
     */
    int noppa = 0;
    /**
     * Sanaselityspelin kesto
     */
    public int sanaselitysKesto = 60;
    boolean hitler = true;
    boolean huora = true;
    boolean tytotVsPojat = true;
    boolean totuusVaiTehtava = true;
    boolean wouldYouRather = true;
    boolean kolmeShottia = true;
    boolean kaksiTotuutta1Valhe = true;
    boolean sanaselitys = true;
    boolean fuckTheDealer = true;
    boolean ravit = true;
    boolean bussikuski = true;
    boolean kasa = true;

    public boolean kaikkitrue(){
        return this.hitler && this.huora && this.tytotVsPojat && this.totuusVaiTehtava && this.wouldYouRather && this.kolmeShottia && this.kaksiTotuutta1Valhe && this.sanaselitys && this.fuckTheDealer && this.ravit && this.bussikuski && this.kasa;
    }

    /**
     * Tyhjä alustaja
     */
    public Peliasetukset () {
    }

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
}
