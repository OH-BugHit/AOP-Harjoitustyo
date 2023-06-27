package com.oh.ready4play;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.oh.ready4play.minipelit.Bussikuski;
import com.oh.ready4play.minipelit.FuckTheDealer;
import com.oh.ready4play.minipelit.Hitler;
import com.oh.ready4play.minipelit.Huora;
import com.oh.ready4play.minipelit.KaksiTotuuttaYksiValhe;
import com.oh.ready4play.minipelit.Kasa;
import com.oh.ready4play.minipelit.KolmeShottia;
import com.oh.ready4play.minipelit.Ravit;
import com.oh.ready4play.minipelit.Sanaselitys;
import com.oh.ready4play.minipelit.TotuusVaiTehtava;
import com.oh.ready4play.minipelit.TytotVsPojat;
import com.oh.ready4play.minipelit.WouldYouRather;

import java.util.ArrayList;

public class Peli extends Fragment {
    private Noppa noppa;
    /**
     * Käytettävät peliasetukset
     */
    public static Peliasetukset peliasetukset;
    /**
     * Seuraava vuoro asetetaan true-tilaan minipelissä sen päättyessä.
     */
    public static volatile boolean seuraavaVuoro = false;

    private static volatile boolean noppaValmis = false;
    /**
     * Tehtävä 11 (epäonnistuessaan) ja 12 siirtävät pelaajaa taaksepäin.
     * Tällä booleanilla toteutetaan taaksepäin siirtämisen pyytäminen, kun noppaa heitetään seuraavan kerran.
     */
    public static volatile boolean tehtavaFail = false;
    /**
     * Korttipakka, 52 korttia, ei jokereita. Tästä pakasta tehdään kopiota minipeleissä joissa vaaditaan kortin poisto pakasta.
     */
    public static ArrayList<Kortti> pakka = new ArrayList<>();
    /**
     * Tämän fragmentin näkymä
     */
    View view;
    /**
     * Pelinappulat pelilaudalla. (Max 10 pelaajaa)
     */
    public static ImageView[] ivNappulat = new ImageView[10];
    /**
     * Pelaajat Pelaaja-olioina
     */
    public static ArrayList<Pelaaja> pelaajat;
    /**
     * Peliruudut pelilaudalla Ruutu-olioina
     */
    private static final ArrayList<Ruutu> peliRuudut = new ArrayList<>();
    /**
     * Vuorossa olevan pelaajan indeksinumero
     */
    public static int vuorossaPelaaja = 0;
    /**
     * Pelissä olevien pelaajien lukumäärä
     */
    public static int pelaajamaara;
    /**
     * Nopanheiton tulos
     */
    private int nopanHeitto;
    /**
     * Peliruudun "toiminto"
     */
    private int toiminto;
    /**
     * Pelilaudan leveys ruutujen sijainnin määrittämiseen
     */
    private int pelilautaX;
    /**
     * Pelilaudan korkeus ruutujen sijainnin määrittämiseen
     */
    private int pelilautaY;
    /**
     * Onko hampurilaisvalikkoa klikattu vai ei
     */
    private boolean hampuriKlikattu = false;
    /**
     * Painike nopan heittämiseen
     */
    private Button btHeitaNoppa;
    /**
     * Painike vuoron ohittamiseen
     */
    private Button btOhita;
    /**
     * Painike tehtävän "Kaksi totuutta yksi valhe", epäonnistumiseen
     */
    private Button btFail;
    /**
     * Painike tehtävän "Kaksi totuutta yksi valhe", onnistumiseen
     */
    private Button bt3;
    /**
     * Vuorossa olevan pelaajan nappulan kuva
     */
    private ImageView ivNappula;
    /**
     * Vuorossa olevan pelaajan niminäkymä
     */
    private TextView tvVuorossaPelaaja;
    private TextView tvAlkuteksti;
    /**
     * Fragment Manager minipelien asettamiseen ja vaihtamiseen
     */
    public static FragmentManager fragmentManager;
    /**
     * FragmentContainerView pelilaudan esittämiseen
     */
    private FragmentContainerView fcvPelilauta;
    /**
     * FragmentContainerView minipelin näyttämiseen
     */
    private FragmentContainerView fcvMinipeliNakyma;

    public Peli() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        alustaKorttipakka();
        peliasetukset = new Peliasetukset();
        lataaAsetukset();

        view = inflater.inflate(R.layout.fragment_peli, container, false);
        ImageView ivNoppa = view.findViewById(R.id.ivNoppa_peli);
        noppa = new Noppa(ivNoppa);

        fcvMinipeliNakyma = view.findViewById(R.id.fcvMinipeliNakyma);
        fcvPelilauta = view.findViewById(R.id.fcvPelilauta);

        //Asetellaan pelinappuloille niiden imageviewit ja asetellaan ne näkymättömiksi
        ivNappulat[0] = view.findViewById(R.id.ivNappula0);
        ivNappulat[1] = view.findViewById(R.id.ivNappula1);
        ivNappulat[2] = view.findViewById(R.id.ivNappula2);
        ivNappulat[3] = view.findViewById(R.id.ivNappula3);
        ivNappulat[4] = view.findViewById(R.id.ivNappula4);
        ivNappulat[5] = view.findViewById(R.id.ivNappula5);
        ivNappulat[6] = view.findViewById(R.id.ivNappula6);
        ivNappulat[7] = view.findViewById(R.id.ivNappula7);
        ivNappulat[8] = view.findViewById(R.id.ivNappula8);
        ivNappulat[9] = view.findViewById(R.id.ivNappula9);
        for (int i = 0; i < 10; i++) {
            ivNappulat[i].setVisibility(View.INVISIBLE);
        }

        fragmentManager = getParentFragmentManager();

        //Haetaan pelaajat muuttujaan uuden pelin luonnissa lisätyt pelaatat
        pelaajat = UusiPeli.itemArrayList;

        TextView tvPaavalikko = view.findViewById(R.id.tvPaavalikko);
        TextView tvLopeta = view.findViewById(R.id.tvLopeta);

        tvPaavalikko.setVisibility(View.INVISIBLE);
        tvLopeta.setVisibility(View.INVISIBLE);

        //Vuorossa olevan pelinappulan imageview. Näkyy pelinäkymän yläreunassa
        ivNappula = view.findViewById(R.id.ivNappulaVuorossa);

        tvAlkuteksti = view.findViewById(R.id.tv_AloitusTeksti_Peli);
        tvVuorossaPelaaja = view.findViewById(R.id.tvPelaajaNimi_Peli);

        btFail = view.findViewById(R.id.btFail_Peli);
        btFail.setVisibility(View.INVISIBLE);
        bt3 = view.findViewById(R.id.bt3_Peli);
        bt3.setVisibility(View.INVISIBLE);

        btHeitaNoppa = view.findViewById(R.id.btHeita_Peli);
        btHeitaNoppa.setVisibility(View.INVISIBLE);
        btOhita = view.findViewById(R.id.btOhita_Peli);
        btOhita.setVisibility(View.INVISIBLE);

        ImageView ivHampuri = view.findViewById(R.id.ivHampuri_peli);

        //Tikapuiden +3 askelta nappula
        bt3.setOnClickListener(e -> {
            pelaajat.get(vuorossaPelaaja).bonusAskeleet = true;
            pelaajat.get(vuorossaPelaaja).kaksiTotuutta = false;
            bt3.setVisibility(View.INVISIBLE);
            btFail.setVisibility(View.INVISIBLE);
        });

        //Tikapuissa jos epäonnistutaan niin painetaan tätä jolloin ei tule bonusaskelia
        btFail.setOnClickListener(e -> {
            pelaajat.get(vuorossaPelaaja).kaksiTotuutta = false;
            bt3.setVisibility(View.INVISIBLE);
            btFail.setVisibility(View.INVISIBLE);
        });

        //Nopanheittonappula
        btHeitaNoppa.setOnClickListener(e -> {
            noppaValmis = false;
            btOhita.setVisibility(View.INVISIBLE);
            btHeitaNoppa.setVisibility(View.INVISIBLE);

            if (toiminto == 11 || toiminto == 12) {
                if (tehtavaFail){
                    liikutaTakaisin();
                    tehtavaFail = false;
                }
            }

            //Noppa heitetään omassa threadissa sen animoimiseksi
            Thread tNoppa = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        nopanHeitto = noppa.heitaNoppaa();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    noppaValmis = true;
                }
            });
            tNoppa.start();

            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    arpaOnHeitetty();
                    MainActivity.INSTANCE.runOnUiThread(Peli.this::nopanAnimaationJalkeen);
                    seuraavanPelaajanVuoro();
                    MainActivity.INSTANCE.runOnUiThread(Peli.this::naytaNapit);
                }

                private void arpaOnHeitetty() {
                    while (!noppaValmis) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            Thread.onSpinWait();
                        }
                    }
                    noppaValmis = false;
                }
            });
            t1.start();
        });

        //Nopanheittonappula päättyy

        //Vuoron ohitusnappula
        btOhita.setOnClickListener(e -> {
            seuraavaVuoro = true;
            seuraavanPelaajanVuoro();
            if (toiminto == 11 || toiminto == 12) {
                if (tehtavaFail){
                    liikutaTakaisin();
                    tehtavaFail = false;
                }
            }
        });

        //Hampurilaisvalikon painaminen
        //TODO: TEE TOASTIT NÄIHIN!!! MUISTA MAINITA ETTEI PELIIN VOI PALATA!
        ivHampuri.setOnClickListener(e -> {
            if (hampuriKlikattu) {
                hampuriKlikattu = false;
                tvPaavalikko.setVisibility(View.INVISIBLE);
                tvLopeta.setVisibility(View.INVISIBLE);
            } else {
                hampuriKlikattu = true;
                tvPaavalikko.setVisibility(View.VISIBLE);
                tvLopeta.setVisibility(View.VISIBLE);
            }
        });

        tvVuorossaPelaaja = view.findViewById(R.id.tvPelaajaNimi_Peli);

        //Hampurilaisvalikon toiminnot: Päävalikko ja Lopeta
        tvPaavalikko.setOnClickListener(e -> {
            Navigation.findNavController(view).navigate(R.id.action_peli_to_alkuvalikko);
        });

        tvLopeta.setOnClickListener(e-> {
            Navigation.findNavController(view).navigate(R.id.action_peli_to_quitFragment2);
        });

        //Aloita peli -nappula ja sen toiminto
        Button btAloita = view.findViewById(R.id.btAloita_Peli);

        btAloita.setOnClickListener(e -> {
            pelilautaX = fcvPelilauta.getWidth();
            pelilautaY = fcvPelilauta.getHeight();
            tvAlkuteksti.setVisibility(View.INVISIBLE);
            alustaPelilauta();
            alustaPeli();
            btAloita.setVisibility(View.INVISIBLE);
        });

        return view;
        }

    /**
     * Nopan animaation jälkeen toteutettava toiminta.
     */
    private void nopanAnimaationJalkeen() {
        toiminto = liikutaPelaajaa(nopanHeitto);
        if (toiminto != 13) {
            suoritaVuoro(toiminto);
        } else {//TODO: GAME OVER! Voittaja on vuorossaoleva
            System.out.println("Pelissä edettiin maaliin!!\nVoittaja on: " + vuorossaPelaaja);
        }
    }


    /**
     * Seuraavan pelaajan vuoron odotus ja asetus kun vuoro voidaan siirtää
     */
    private void seuraavanPelaajanVuoro() {
        while (!seuraavaVuoro) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Thread.onSpinWait();
            }
        }
        seuraavaVuoro = false;
        if (vuorossaPelaaja == pelaajat.size()-1) {
            vuorossaPelaaja = 0;
        } else {
            vuorossaPelaaja ++;
        }
        MainActivity.INSTANCE.runOnUiThread(Peli.this::asetaSeuraava);
    }

    /**
     * Jos tehtävä 11 tai 12 epäonnistuu. Palataan laudalla taaksepäin.
     * liikutaTakaisin toteuttaa lisaSiirtojen määrän verran liikettä.
     */
    private void liikutaTakaisin() {
        //Tarkastellaan edellinen pelaaja
        int edellinenPelaaja;
        if (vuorossaPelaaja == 0) {
            edellinenPelaaja = pelaajamaara - 1;
        } else {
            edellinenPelaaja = vuorossaPelaaja - 1;
        }
        peliRuudut.get(pelaajat.get(edellinenPelaaja).sijainti).pelaajiaRuudussa --;
        int uusiruutu = pelaajat.get(edellinenPelaaja).sijainti + peliRuudut.get(pelaajat.get(edellinenPelaaja).sijainti).lisaSiirrot;
        peliRuudut.get(uusiruutu).pelaajiaRuudussa ++;
        Pelaaja.liikutaPelaajaRuutuun(pelaajat.get(edellinenPelaaja),peliRuudut.get(uusiruutu));
    }


    /**
     * Seuraavan pelivuorossa olevan pelaajan tiedot näkyville
     */
    private void asetaSeuraava() {
        String asetettava = " " + pelaajat.get(vuorossaPelaaja).pelaajanimi;
        tvVuorossaPelaaja.setText(asetettava);
        ivNappula.setImageDrawable(pelaajat.get(vuorossaPelaaja).pelaajakuva);
    }

    /**
     * Vuoron toiminnon suorittaminen (minipelin käynnistys)
     * @param toiminto Suoritettava toiminto (vastaa pelilaudan ruudun toimintonumeroa)
     */
    private void suoritaVuoro(int toiminto) {
        bt3.setVisibility(View.INVISIBLE);
        btFail.setVisibility(View.INVISIBLE);
        switch (toiminto) {
            case 1 -> fragmentManager.beginTransaction()
                    .replace(R.id.fcvMinipeliNakyma,Hitler.class,null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
            case 2 -> fragmentManager.beginTransaction()
                    .replace(R.id.fcvMinipeliNakyma, Huora.class,null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
            case 3 -> fragmentManager.beginTransaction()
                    .replace(R.id.fcvMinipeliNakyma, TytotVsPojat.class,null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
            case 4 -> fragmentManager.beginTransaction()
                    .replace(R.id.fcvMinipeliNakyma, TotuusVaiTehtava.class,null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
            case 5 -> fragmentManager.beginTransaction()
                    .replace(R.id.fcvMinipeliNakyma, WouldYouRather.class,null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
            case 6 -> fragmentManager.beginTransaction()
                    .replace(R.id.fcvMinipeliNakyma, KolmeShottia.class,null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
            case 7 -> fragmentManager.beginTransaction()
                    .replace(R.id.fcvMinipeliNakyma, KaksiTotuuttaYksiValhe.class,null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
            case 8 -> fragmentManager.beginTransaction()
                    .replace(R.id.fcvMinipeliNakyma, Sanaselitys.class,null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
            case 9 -> fragmentManager.beginTransaction()
                    .replace(R.id.fcvMinipeliNakyma, FuckTheDealer.class,null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
            case 10 -> fragmentManager.beginTransaction()
                    .replace(R.id.fcvMinipeliNakyma, Ravit.class,null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
            case 11 -> fragmentManager.beginTransaction()
                    .replace(R.id.fcvMinipeliNakyma, Bussikuski.class,null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
            case 12 -> fragmentManager.beginTransaction()
                    .replace(R.id.fcvMinipeliNakyma, Kasa.class,null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
        }
    }

    /**
     * Liikuttaa pelaajaa pelilaudalla
     * Jos ruutu johon päädytään ei ole käytössä asetuksissa, mennään seuraavaan ruutuun. (Toistuu kunnes ruutu ok, viimeistään maalissa!)
     * Päivittää ruudussa olevien pelaajien lukumäärän. Tätä lukumäärää käytetään sijoittelemaan useampi pelaaja samaan ruutuun.
     * @param nopanHeitto Saa arvokseen nopanheiton tuloksen
     * @return Palauttaa saavutun ruudun tehtävän
     */
    private int liikutaPelaajaa(int nopanHeitto) {
        peliRuudut.get(pelaajat.get(vuorossaPelaaja).sijainti).pelaajiaRuudussa --;
        int uusiruutu = pelaajat.get(vuorossaPelaaja).sijainti + nopanHeitto;
        boolean ruutuOk = peliasetukset.kaikkitrue();
        while (!ruutuOk) {
            if (peliRuudut.get(uusiruutu).tehtava == 1) {
                if (peliasetukset.hitler) {
                    ruutuOk = true;
                } else {
                    uusiruutu++;
                }
            }
            if (peliRuudut.get(uusiruutu).tehtava == 2) {
                if (peliasetukset.huora) {
                    ruutuOk = true;
                } else {
                    uusiruutu++;
                }
            }

            if (peliRuudut.get(uusiruutu).tehtava == 3) {
                if (peliasetukset.tytotVsPojat) {
                    ruutuOk = true;
                } else {
                    uusiruutu++;
                }
            }

            if (peliRuudut.get(uusiruutu).tehtava == 4) {
                if (peliasetukset.totuusVaiTehtava) {
                    ruutuOk = true;
                } else {
                    uusiruutu++;
                }
            }
            if (peliRuudut.get(uusiruutu).tehtava == 5) {
                if (peliasetukset.wouldYouRather) {
                    ruutuOk = true;
                } else {
                    uusiruutu++;
                }
            }

            if (peliRuudut.get(uusiruutu).tehtava == 6) {
                if (peliasetukset.kolmeShottia) {
                    ruutuOk = true;
                } else {
                    uusiruutu++;
                }
            }

            if (peliRuudut.get(uusiruutu).tehtava == 7) {
                if (peliasetukset.kaksiTotuutta1Valhe) {
                    ruutuOk = true;
                } else {
                    uusiruutu++;
                }
            }

            if (peliRuudut.get(uusiruutu).tehtava == 8) {
                if (peliasetukset.sanaselitys) {
                    ruutuOk = true;
                } else {
                    uusiruutu++;
                }
            }

            if (peliRuudut.get(uusiruutu).tehtava == 9) {
                if (peliasetukset.fuckTheDealer) {
                    ruutuOk = true;
                } else {
                    uusiruutu++;
                }
            }

            if (peliRuudut.get(uusiruutu).tehtava == 10) {
                if (peliasetukset.ravit) {
                    ruutuOk = true;
                } else {
                    uusiruutu++;
                }
            }

            if (peliRuudut.get(uusiruutu).tehtava == 11) {
                if (peliasetukset.bussikuski) {
                    ruutuOk = true;
                } else {
                    uusiruutu++;
                }
            }

            if (peliRuudut.get(uusiruutu).tehtava == 12) {
                if (peliasetukset.kasa) {
                    ruutuOk = true;
                } else {
                    uusiruutu++;
                }
            }

            if (peliRuudut.get(uusiruutu).tehtava == 13) {
                ruutuOk = true;
            }
        }
        peliRuudut.get(uusiruutu).pelaajiaRuudussa ++;
        Pelaaja.liikutaPelaajaRuutuun(pelaajat.get(vuorossaPelaaja),peliRuudut.get(uusiruutu));
        return peliRuudut.get(uusiruutu).tehtava;
    }

    /**
     * Metodi "Kaksi totuutta, Yksi valhe" -pelin jälkeisten näppäinten näyttämiseen
     */
    public void naytaNapit() {
        btOhita.setVisibility(View.VISIBLE);
        btHeitaNoppa.setVisibility(View.VISIBLE);
        if (pelaajat.get(vuorossaPelaaja).kaksiTotuutta) {
            bt3.setVisibility(View.VISIBLE);
            btFail.setVisibility(View.VISIBLE);
        } else {
            bt3.setVisibility(View.INVISIBLE);
            btFail.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Pelaajien ja pelin alustus
     */
    private void alustaPeli() {
        btHeitaNoppa.setVisibility(View.VISIBLE);
        btOhita.setVisibility(View.VISIBLE);
        vuorossaPelaaja = 0;
        pelaajamaara = pelaajat.size();
        int i = 0;
        for (Pelaaja pelaaja : pelaajat) {
            pelaaja.imageView = ivNappulat[i];
            pelaaja.imageView.setImageDrawable(pelaaja.pelaajakuva);
            pelaaja.imageView.setVisibility(View.VISIBLE);
            Pelaaja.liikutaPelaajaRuutuun(pelaaja, peliRuudut.get(0));
            i++;
        }
        ivNappula.setImageDrawable(pelaajat.get(vuorossaPelaaja).pelaajakuva);
        String asetettava = " " + pelaajat.get(vuorossaPelaaja).pelaajanimi;
        tvVuorossaPelaaja.setText(asetettava);
    }

    /**
     * Luodaan korttipakka (tavallinen, 52 korttia, ei jokereita)
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    private void alustaKorttipakka() {
        for (int i = 0; i < 52; i++) {
            Kortti kortti = new Kortti();
            if (i < 13) {
                kortti.arvo = i+1;
                kortti.maa = "Hertta";
            } else if (i < 26) {
                kortti.arvo = i-12;
                kortti.maa = "Ruutu";
            } else if (i < 39) {
                kortti.arvo = i-25;
                kortti.maa = "Pata";
            } else {
                kortti.arvo = i-38;
                kortti.maa = "Risti";
            }
            switch (i) {
                case 0 -> kortti.kuva = getResources().getDrawable(R.drawable.hertta1,MainActivity.INSTANCE.getTheme());
                case 1 -> kortti.kuva = getResources().getDrawable(R.drawable.hertta2,MainActivity.INSTANCE.getTheme());
                case 2 -> kortti.kuva = getResources().getDrawable(R.drawable.hertta3,MainActivity.INSTANCE.getTheme());
                case 3 -> kortti.kuva = getResources().getDrawable(R.drawable.hertta4,MainActivity.INSTANCE.getTheme());
                case 4 -> kortti.kuva = getResources().getDrawable(R.drawable.hertta5,MainActivity.INSTANCE.getTheme());
                case 5 -> kortti.kuva = getResources().getDrawable(R.drawable.hertta6,MainActivity.INSTANCE.getTheme());
                case 6 -> kortti.kuva = getResources().getDrawable(R.drawable.hertta7,MainActivity.INSTANCE.getTheme());
                case 7 -> kortti.kuva = getResources().getDrawable(R.drawable.hertta8,MainActivity.INSTANCE.getTheme());
                case 8 -> kortti.kuva = getResources().getDrawable(R.drawable.hertta9,MainActivity.INSTANCE.getTheme());
                case 9 -> kortti.kuva = getResources().getDrawable(R.drawable.hertta10,MainActivity.INSTANCE.getTheme());
                case 10 -> kortti.kuva = getResources().getDrawable(R.drawable.hertta11,MainActivity.INSTANCE.getTheme());
                case 11 -> kortti.kuva = getResources().getDrawable(R.drawable.hertta12,MainActivity.INSTANCE.getTheme());
                case 12 -> kortti.kuva = getResources().getDrawable(R.drawable.hertta13,MainActivity.INSTANCE.getTheme());
                case 13 -> kortti.kuva = getResources().getDrawable(R.drawable.ruutu1,MainActivity.INSTANCE.getTheme());
                case 14 -> kortti.kuva = getResources().getDrawable(R.drawable.ruutu2,MainActivity.INSTANCE.getTheme());
                case 15 -> kortti.kuva = getResources().getDrawable(R.drawable.ruutu3,MainActivity.INSTANCE.getTheme());
                case 16 -> kortti.kuva = getResources().getDrawable(R.drawable.ruutu4,MainActivity.INSTANCE.getTheme());
                case 17 -> kortti.kuva = getResources().getDrawable(R.drawable.ruutu5,MainActivity.INSTANCE.getTheme());
                case 18 -> kortti.kuva = getResources().getDrawable(R.drawable.ruutu6,MainActivity.INSTANCE.getTheme());
                case 19 -> kortti.kuva = getResources().getDrawable(R.drawable.ruutu7,MainActivity.INSTANCE.getTheme());
                case 20 -> kortti.kuva = getResources().getDrawable(R.drawable.ruutu8,MainActivity.INSTANCE.getTheme());
                case 21 -> kortti.kuva = getResources().getDrawable(R.drawable.ruutu9,MainActivity.INSTANCE.getTheme());
                case 22 -> kortti.kuva = getResources().getDrawable(R.drawable.ruutu10,MainActivity.INSTANCE.getTheme());
                case 23 -> kortti.kuva = getResources().getDrawable(R.drawable.ruutu11,MainActivity.INSTANCE.getTheme());
                case 24 -> kortti.kuva = getResources().getDrawable(R.drawable.ruutu12,MainActivity.INSTANCE.getTheme());
                case 25 -> kortti.kuva = getResources().getDrawable(R.drawable.ruutu13,MainActivity.INSTANCE.getTheme());
                case 26 -> kortti.kuva = getResources().getDrawable(R.drawable.pata1,MainActivity.INSTANCE.getTheme());
                case 27 -> kortti.kuva = getResources().getDrawable(R.drawable.pata2,MainActivity.INSTANCE.getTheme());
                case 28 -> kortti.kuva = getResources().getDrawable(R.drawable.pata3,MainActivity.INSTANCE.getTheme());
                case 29 -> kortti.kuva = getResources().getDrawable(R.drawable.pata4,MainActivity.INSTANCE.getTheme());
                case 30 -> kortti.kuva = getResources().getDrawable(R.drawable.pata5,MainActivity.INSTANCE.getTheme());
                case 31 -> kortti.kuva = getResources().getDrawable(R.drawable.pata6,MainActivity.INSTANCE.getTheme());
                case 32 -> kortti.kuva = getResources().getDrawable(R.drawable.pata7,MainActivity.INSTANCE.getTheme());
                case 33 -> kortti.kuva = getResources().getDrawable(R.drawable.pata8,MainActivity.INSTANCE.getTheme());
                case 34 -> kortti.kuva = getResources().getDrawable(R.drawable.pata9,MainActivity.INSTANCE.getTheme());
                case 35 -> kortti.kuva = getResources().getDrawable(R.drawable.pata10,MainActivity.INSTANCE.getTheme());
                case 36 -> kortti.kuva = getResources().getDrawable(R.drawable.pata11,MainActivity.INSTANCE.getTheme());
                case 37 -> kortti.kuva = getResources().getDrawable(R.drawable.pata12,MainActivity.INSTANCE.getTheme());
                case 38 -> kortti.kuva = getResources().getDrawable(R.drawable.pata13,MainActivity.INSTANCE.getTheme());
                case 39 -> kortti.kuva = getResources().getDrawable(R.drawable.risti1,MainActivity.INSTANCE.getTheme());
                case 40 -> kortti.kuva = getResources().getDrawable(R.drawable.risti2,MainActivity.INSTANCE.getTheme());
                case 41 -> kortti.kuva = getResources().getDrawable(R.drawable.risti3,MainActivity.INSTANCE.getTheme());
                case 42 -> kortti.kuva = getResources().getDrawable(R.drawable.risti4,MainActivity.INSTANCE.getTheme());
                case 43 -> kortti.kuva = getResources().getDrawable(R.drawable.risti5,MainActivity.INSTANCE.getTheme());
                case 44 -> kortti.kuva = getResources().getDrawable(R.drawable.risti6,MainActivity.INSTANCE.getTheme());
                case 45 -> kortti.kuva = getResources().getDrawable(R.drawable.risti7,MainActivity.INSTANCE.getTheme());
                case 46 -> kortti.kuva = getResources().getDrawable(R.drawable.risti8,MainActivity.INSTANCE.getTheme());
                case 47 -> kortti.kuva = getResources().getDrawable(R.drawable.risti9,MainActivity.INSTANCE.getTheme());
                case 48 -> kortti.kuva = getResources().getDrawable(R.drawable.risti10,MainActivity.INSTANCE.getTheme());
                case 49 -> kortti.kuva = getResources().getDrawable(R.drawable.risti11,MainActivity.INSTANCE.getTheme());
                case 50 -> kortti.kuva = getResources().getDrawable(R.drawable.risti12,MainActivity.INSTANCE.getTheme());
                case 51 -> kortti.kuva = getResources().getDrawable(R.drawable.risti13,MainActivity.INSTANCE.getTheme());
            }
            pakka.add(kortti);
        }
    }

    /**
     * Asetetaan pelilaudan ruudut ja niiden ominaisuudet
     */
    private void alustaPelilauta() {
        int pelilaudanKoko = 62;
        for (int i = 0; i < pelilaudanKoko; i++) {
            Ruutu ruutu = new Ruutu();
            Sijainti sijainti = new Sijainti();
            switch (i) {
                case 0 -> {
                    sijainti.x = laskeX(0.9221);
                    sijainti.y = laskeY(0.7846);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 0;
                    ruutu.ruudunNumero = 0;
                }
                case 1 -> {
                    sijainti.x = laskeX(0.8701);
                    sijainti.y = laskeY(0.8042);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 4;
                    ruutu.ruudunNumero = 1;
                }
                case 2 -> {
                    sijainti.x = laskeX(0.8136);
                    sijainti.y = laskeY(0.8163);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 8;
                    ruutu.ruudunNumero = 2;
                }
                case 3 -> {
                    sijainti.x = laskeX(0.7586);
                    sijainti.y = laskeY(0.8283);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 1;
                    ruutu.ruudunNumero = 3;
                }
                case 4 -> {
                    sijainti.x = laskeX(0.6990);
                    sijainti.y = laskeY(0.8343);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 6;
                    ruutu.ruudunNumero = 4;
                }
                case 5 -> {
                    sijainti.x = laskeX(0.6448);
                    sijainti.y = laskeY(0.8434);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 3;
                    ruutu.ruudunNumero = 5;
                }
                case 6 -> {
                    sijainti.x = laskeX(0.5913);
                    sijainti.y = laskeY(0.8434);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 9;
                    ruutu.ruudunNumero = 6;
                }
                case 7 -> {
                    sijainti.x = laskeX(0.5371);
                    sijainti.y = laskeY(0.8404);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 4;
                    ruutu.ruudunNumero = 7;
                }
                case 8 -> {
                    sijainti.x = laskeX(0.4828);
                    sijainti.y = laskeY(0.8554);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 10;
                    ruutu.ruudunNumero = 8;
                }
                case 9 -> {
                    sijainti.x = laskeX(0.4316);
                    sijainti.y = laskeY(0.8645);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 8;
                    ruutu.ruudunNumero = 9;
                }
                case 10 -> {
                    sijainti.x = laskeX(0.3797);
                    sijainti.y = laskeY(0.8705);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 2;
                    ruutu.ruudunNumero = 10;
                }
                case 11 -> {
                    sijainti.x = laskeX(0.3285);
                    sijainti.y = laskeY(0.8630);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 11;
                    ruutu.ruudunNumero = 11;
                    ruutu.lisaSiirrot = -5;
                }
                case 12 -> {
                    sijainti.x = laskeX(0.2804);
                    sijainti.y = laskeY(0.8419);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 5;
                    ruutu.ruudunNumero = 12;
                }
                case 13 -> {
                    sijainti.x = laskeX(0.2261);
                    sijainti.y = laskeY(0.8208);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 6;
                    ruutu.ruudunNumero = 13;
                }
                case 14 -> {
                    sijainti.x = laskeX(0.1833);
                    sijainti.y = laskeY(0.7741);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 3;
                    ruutu.ruudunNumero = 14;
                }
                case 15 -> {
                    sijainti.x = laskeX(0.1383);
                    sijainti.y = laskeY(0.7199);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 7;
                    ruutu.ruudunNumero = 15;
                    ruutu.lisaSiirrot = 3;
                }
                case 16 -> {
                    sijainti.x = laskeX(0.1115);
                    sijainti.y = laskeY(0.6280);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 4;
                    ruutu.ruudunNumero = 16;
                }
                case 17 -> {
                    sijainti.x = laskeX(0.1062);
                    sijainti.y = laskeY(0.5211);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 6;
                    ruutu.ruudunNumero = 17;
                }
                case 18 -> {
                    sijainti.x = laskeX(0.1192);
                    sijainti.y = laskeY(0.4232);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 1;
                    ruutu.ruudunNumero = 18;
                }
                case 19 -> {
                    sijainti.x = laskeX(0.1421);
                    sijainti.y = laskeY(0.3298);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 8;
                    ruutu.ruudunNumero = 19;
                }
                case 20 -> {
                    sijainti.x = laskeX(0.1772);
                    sijainti.y = laskeY(0.2440);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 5;
                    ruutu.ruudunNumero = 20;
                }
                case 21 -> {
                    sijainti.x = laskeX(0.2223);
                    sijainti.y = laskeY(0.1792);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 10;
                    ruutu.ruudunNumero = 21;
                }
                //EI VIELÄ
                case 22 -> {
                    sijainti.x = laskeX(0.2712);
                    sijainti.y = laskeY(0.1355);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 6;
                    ruutu.ruudunNumero = 22;
                }
                case 23 -> {
                    sijainti.x = laskeX(0.3231);
                    sijainti.y = laskeY(0.1039);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 9;
                    ruutu.ruudunNumero = 23;
                }
                case 24 -> {
                    sijainti.x = laskeX(0.3759);
                    sijainti.y = laskeY(0.0889);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 3;
                    ruutu.ruudunNumero = 24;
                }
                case 25 -> {
                    sijainti.x = laskeX(0.4270);
                    sijainti.y = laskeY(0.0878);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 7;
                    ruutu.ruudunNumero = 25;
                    ruutu.lisaSiirrot = 3;
                }
                case 26 -> {
                    sijainti.x = laskeX(0.4798);
                    sijainti.y = laskeY(0.0873);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 8;
                    ruutu.ruudunNumero = 26;
                }
                case 27 -> {
                    sijainti.x = laskeX(0.5317);
                    sijainti.y = laskeY(0.0904);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 1;
                    ruutu.ruudunNumero = 27;
                }
                case 28 -> {
                    sijainti.x = laskeX(0.5867);
                    sijainti.y = laskeY(0.0919);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 5;
                    ruutu.ruudunNumero = 28;
                }
                case 29 -> {
                    sijainti.x = laskeX(0.6425);
                    sijainti.y = laskeY(0.1054);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 10;
                    ruutu.ruudunNumero = 29;
                }
                case 30 -> {
                    sijainti.x = laskeX(0.6952);
                    sijainti.y = laskeY(0.1146);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 2;
                    ruutu.ruudunNumero = 30;
                }
                case 31 -> {
                    sijainti.x = laskeX(0.7479);
                    sijainti.y = laskeY(0.1988);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 9;
                    ruutu.ruudunNumero = 31;
                }
                case 32 -> {
                    sijainti.x = laskeX(0.7991);
                    sijainti.y = laskeY(0.2696);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 3;
                    ruutu.ruudunNumero = 32;
                }
                case 33 -> {
                    sijainti.x = laskeX(0.8434);
                    sijainti.y = laskeY(0.3584);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 10;
                    ruutu.ruudunNumero = 33;
                }
                case 34 -> {
                    sijainti.x = laskeX(0.8564);
                    sijainti.y = laskeY(0.4578);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 1;
                    ruutu.ruudunNumero = 34;
                }
                case 35 -> {
                    sijainti.x = laskeX(0.8457);
                    sijainti.y = laskeY(0.5572);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 6;
                    ruutu.ruudunNumero = 35;
                }
                case 36 -> {
                    sijainti.x = laskeX(0.8037);
                    sijainti.y = laskeY(0.6265);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 4;
                    ruutu.ruudunNumero = 36;
                }
                case 37 -> {
                    sijainti.x = laskeX(0.7464);
                    sijainti.y = laskeY(0.6476);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 5;
                    ruutu.ruudunNumero = 37;
                }
                case 38 -> {
                    sijainti.x = laskeX(0.6960);
                    sijainti.y = laskeY(0.6777);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 3;
                    ruutu.ruudunNumero = 38;
                }
                case 39 -> {
                    sijainti.x = laskeX(0.6425);
                    sijainti.y = laskeY(0.6913);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 11;
                    ruutu.ruudunNumero = 39;
                    ruutu.lisaSiirrot = -4;
                }
                case 40 -> {
                    sijainti.x = laskeX(0.5882);
                    sijainti.y = laskeY(0.6943);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 9;
                    ruutu.ruudunNumero = 40;
                }
                case 41 -> {
                    sijainti.x = laskeX(0.5317);
                    sijainti.y = laskeY(0.6792);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 6;
                    ruutu.ruudunNumero = 41;
                }
                case 42 -> {
                    sijainti.x = laskeX(0.4736);
                    sijainti.y = laskeY(0.6566);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 10;
                    ruutu.ruudunNumero = 42;
                }
                case 43 -> {
                    sijainti.x = laskeX(0.3186);
                    sijainti.y = laskeY(0.6280);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 1;
                    ruutu.ruudunNumero = 43;
                }
                case 44 -> {
                    sijainti.x = laskeX(0.3583);
                    sijainti.y = laskeY(0.6024);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 4;
                    ruutu.ruudunNumero = 44;
                }
                case 45 -> {
                    sijainti.x = laskeX(0.3102);
                    sijainti.y = laskeY(0.5663);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 9;
                    ruutu.ruudunNumero = 45;
                }
                case 46 -> {
                    sijainti.x = laskeX(0.2735);
                    sijainti.y = laskeY(0.5151);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 8;
                    ruutu.ruudunNumero = 46;
                }
                case 47 -> {
                    sijainti.x = laskeX(0.2636);
                    sijainti.y = laskeY(0.4232);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 4;
                    ruutu.ruudunNumero = 47;
                }
                case 48 -> {
                    sijainti.x = laskeX(0.3040);
                    sijainti.y = laskeY(0.3569);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 11;
                    ruutu.ruudunNumero = 48;
                    ruutu.lisaSiirrot = -5;
                }
                case 49 -> {
                    sijainti.x = laskeX(0.3461);
                    sijainti.y = laskeY(0.3087);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 3;
                    ruutu.ruudunNumero = 49;
                }
                case 50 -> {
                    sijainti.x = laskeX(0.3858);
                    sijainti.y = laskeY(0.2711);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 9;
                    ruutu.ruudunNumero = 50;
                }
                case 51 -> {
                    sijainti.x = laskeX(0.4408);
                    sijainti.y = laskeY(0.2756);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 2;
                    ruutu.ruudunNumero = 51;
                }
                case 52 -> {
                    sijainti.x = laskeX(0.4923);
                    sijainti.y = laskeY(0.2771);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 11;
                    ruutu.ruudunNumero = 52;
                    ruutu.lisaSiirrot = -5;
                }
                case 53 -> {
                    sijainti.x = laskeX(0.5439);
                    sijainti.y = laskeY(0.2711);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 5;
                    ruutu.ruudunNumero = 53;
                }
                case 54 -> {
                    sijainti.x = laskeX(0.5959);
                    sijainti.y = laskeY(0.2726);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 8;
                    ruutu.ruudunNumero = 54;
                }
                case 55 -> {
                    sijainti.x = laskeX(0.6440);
                    sijainti.y = laskeY(0.2997);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 4;
                    ruutu.ruudunNumero = 55;
                }
                case 56 -> {
                    sijainti.x = laskeX(0.6700);
                    sijainti.y = laskeY(0.3961);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 12;
                    ruutu.ruudunNumero = 56;
                    ruutu.lisaSiirrot = -18;
                }
                case 57 -> {
                    sijainti.x = laskeX(0.6241);
                    sijainti.y = laskeY(0.4488);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 7;
                    ruutu.ruudunNumero = 57;
                    ruutu.lisaSiirrot = 3;
                }
                case 58 -> {
                    sijainti.x = laskeX(0.5699);
                    sijainti.y = laskeY(0.4488);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 5;
                    ruutu.ruudunNumero = 58;
                }
                case 59 -> {
                    sijainti.x = laskeX(0.5149);
                    sijainti.y = laskeY(0.4608);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 1;
                    ruutu.ruudunNumero = 59;
                }
                case 60 -> {
                    sijainti.x = laskeX(0.4645);
                    sijainti.y = laskeY(0.4654);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 3;
                    ruutu.ruudunNumero = 60;
                }
                case 61 -> {
                    sijainti.x = laskeX(0.4018);
                    sijainti.y = laskeY(0.4578);
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 13;
                    ruutu.ruudunNumero = 61;
                }
            }
            peliRuudut.add(ruutu);
        }
    }

    /**
     * Laskee sijainnin Y-kordinaatistolla
     * @param suhdeKerroin suhteellinen sijainti Y-kordinaatistolla 0-1
     * @return Palauttaa todellisen sijainnin näytöllä Y-koordinaatistossa
     */
    private float laskeY(double suhdeKerroin) {
        return Float.parseFloat(String.valueOf(pelilautaY * suhdeKerroin)) + fcvMinipeliNakyma.getHeight() - fcvPelilauta.getHeight();
    }

    /**
     * Laskee sijainnin X-kordinaatistolla
     * @param suhdeKerroin suhteellinen sijainti X-kordinaatistolla 0-1
     * @return Palauttaa todellisen sijainnin näytöllä X-koordinaatistossa
     */
    private float laskeX(double suhdeKerroin) {
        return Float.parseFloat(String.valueOf(pelilautaX * suhdeKerroin));
    }

    /**
     * Lataa käytettävät asetukset
     */
    private void lataaAsetukset() {
        Alkuvalikko.sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        peliasetukset.hitler = lataaTehtavaKaytossa(getString(R.string.saved_task_hitler));
        peliasetukset.huora = lataaTehtavaKaytossa(getString(R.string.saved_task_hoe));
        peliasetukset.tytotVsPojat = lataaTehtavaKaytossa(getString(R.string.saved_task_girlsvsboys));
        peliasetukset.totuusVaiTehtava = lataaTehtavaKaytossa(getString(R.string.saved_task_truthordare));
        peliasetukset.wouldYouRather = lataaTehtavaKaytossa(getString(R.string.saved_task_wouldyourahter));
        peliasetukset.kolmeShottia = lataaTehtavaKaytossa(getString(R.string.saved_task_3shots));
        peliasetukset.kaksiTotuutta1Valhe = lataaTehtavaKaytossa(getString(R.string.saved_task_2truths1lie));
        peliasetukset.sanaselitys = lataaTehtavaKaytossa(getString(R.string.saved_task_dictionary));
        peliasetukset.fuckTheDealer = lataaTehtavaKaytossa(getString(R.string.saved_task_fthedealer));
        peliasetukset.ravit = lataaTehtavaKaytossa(getString(R.string.saved_task_horserace));
        peliasetukset.bussikuski = lataaTehtavaKaytossa(getString(R.string.saved_task_busdriver));
        peliasetukset.kasa = lataaTehtavaKaytossa(getString(R.string.saved_task_stack));

        peliasetukset.noppa = Alkuvalikko.sharedPref.getInt(getString(R.string.saved_dice), 0);
        peliasetukset.sanaselitysKesto = Alkuvalikko.sharedPref.getInt(getString(R.string.saved_durationDictionary),60);
    }

    /**
     * Extraktoitu metodi tehtävän käytettävyyden lataamiseen
     * @param avain avainsana, jolla asetus on tallennettu
     * @return palauttaa FALSE jos tehtävä ei käytössä, muuten TRUE
     */
    private boolean lataaTehtavaKaytossa(String avain) {
        boolean taskKaytossa;
        boolean defaultValue = true;
        taskKaytossa = Alkuvalikko.sharedPref.getBoolean(avain,defaultValue);
        return taskKaytossa;
    }

}