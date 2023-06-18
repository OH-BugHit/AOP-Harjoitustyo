package com.oh.ready4play;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import android.text.method.TextKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.oh.ready4play.minipelit.FuckTheDealer;
import com.oh.ready4play.minipelit.Hitler;
import com.oh.ready4play.minipelit.Huora;
import com.oh.ready4play.minipelit.KaksiTotuuttaYksiValhe;
import com.oh.ready4play.minipelit.KolmeShottia;
import com.oh.ready4play.minipelit.Ravit;
import com.oh.ready4play.minipelit.Sanaselitys;
import com.oh.ready4play.minipelit.TotuusVaiTehtava;
import com.oh.ready4play.minipelit.TytotVsPojat;
import com.oh.ready4play.minipelit.WouldYouRather;

import java.util.ArrayList;

public class Peli extends Fragment {
    public static volatile boolean seuraavaVuoro = false;
    public static ArrayList<Kortti> pakka = new ArrayList<>();
    View view;
    public static ImageView[] ivNappulat = new ImageView[10];
    public static ArrayList<Pelaaja> pelaajat;
    private static final ArrayList<Ruutu> peliRuudut = new ArrayList<>();
    public static int vuorossaPelaaja = 0;
    public static int pelaajamaara;
    private int toiminto;
    private boolean hampuriKlikattu = false;
    Button btHeitaNoppa;
    Button btOhita;
    Button btFail;
    Button bt3;
    ImageView ivNappula;
    TextView tvVuorossaPelaaja;
    TextView tvOhitaPelaajanimi;
    public static FragmentManager fragmentManager;
    public Peli() {
        // Required empty public constructor
    }
    public void naytaNapit() {
        btOhita.setVisibility(View.VISIBLE);
        btHeitaNoppa.setVisibility(View.VISIBLE);
        tvOhitaPelaajanimi.setEnabled(true);
        if (pelaajat.get(vuorossaPelaaja).kaksiTotuutta) {
            bt3.setVisibility(View.VISIBLE);
            btFail.setVisibility(View.VISIBLE);
        } else {
            bt3.setVisibility(View.INVISIBLE);
            btFail.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        alustaKorttipakka();

        view = inflater.inflate(R.layout.fragment_peli, container, false);

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

        pelaajat = UusiPeli.itemArrayList;

        tvOhitaPelaajanimi = view.findViewById(R.id.tvOhitaJatkossa);
        TextView tvPaavalikko = view.findViewById(R.id.tvPaavalikko);
        TextView tvLopeta = view.findViewById(R.id.tvLopeta);

        tvOhitaPelaajanimi.setVisibility(View.INVISIBLE);
        tvPaavalikko.setVisibility(View.INVISIBLE);
        tvLopeta.setVisibility(View.INVISIBLE);

        ivNappula = view.findViewById(R.id.ivNappulaVuorossa);

        tvVuorossaPelaaja = view.findViewById(R.id.tvPelaajaNimi_Peli);

        btFail = view.findViewById(R.id.btFail_Peli);
        btFail.setVisibility(View.INVISIBLE);
        bt3 = view.findViewById(R.id.bt3_Peli);
        bt3.setVisibility(View.INVISIBLE);

        btHeitaNoppa = view.findViewById(R.id.btHeita_Peli);
        btOhita = view.findViewById(R.id.btOhita_Peli);

        ImageView ivHampuri = view.findViewById(R.id.ivHampuri_peli);

        bt3.setOnClickListener(e -> {
            pelaajat.get(vuorossaPelaaja).bonusAskeleet = true;
            pelaajat.get(vuorossaPelaaja).kaksiTotuutta = false;
            bt3.setVisibility(View.INVISIBLE);
            btFail.setVisibility(View.INVISIBLE);
        });

        btFail.setOnClickListener(e -> {
            pelaajat.get(vuorossaPelaaja).kaksiTotuutta = false;
            bt3.setVisibility(View.INVISIBLE);
            btFail.setVisibility(View.INVISIBLE);
        });

        btHeitaNoppa.setOnClickListener(e -> {

            btOhita.setVisibility(View.INVISIBLE);
            btHeitaNoppa.setVisibility(View.INVISIBLE);
            tvOhitaPelaajanimi.setEnabled(false);

            int nopanHeitto = Noppa.heitaNoppaa();
            toiminto = liikutaPelaajaa(nopanHeitto);
            if (toiminto != 13) {
                suoritaVuoro(toiminto);
            } else {//TODO: GAME OVER! Voittaja on vuorossaoleva
                System.out.println("Pelissä edettiin maaliin!!\nVoittaja on: " + vuorossaPelaaja);
            }
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    seuraavanPelaajanVuoro();
                    MainActivity.INSTANCE.runOnUiThread(Peli.this::naytaNapit);
                }
            });
            t1.start();
        });

        ivHampuri.setOnClickListener(e -> {
            if (hampuriKlikattu) {
                hampuriKlikattu = false;
                tvOhitaPelaajanimi.setVisibility(View.INVISIBLE);
                tvPaavalikko.setVisibility(View.INVISIBLE);
                tvLopeta.setVisibility(View.INVISIBLE);
            } else {
                hampuriKlikattu = true;
                tvOhitaPelaajanimi.setVisibility(View.VISIBLE);
                tvPaavalikko.setVisibility(View.VISIBLE);
                tvLopeta.setVisibility(View.VISIBLE);
            }
        });


        tvOhitaPelaajanimi.setOnClickListener(e -> {
            //TODO: Tee tänne vuoron ohitus
        });

        tvPaavalikko.setOnClickListener(e -> {
            Navigation.findNavController(view).navigate(R.id.action_peli_to_alkuvalikko);
        });

        tvLopeta.setOnClickListener(e-> {
            Navigation.findNavController(view).navigate(R.id.action_peli_to_quitFragment);
        });

        btHeitaNoppa = view.findViewById(R.id.btHeita_Peli);
        btOhita = view.findViewById(R.id.btOhita_Peli);

        tvVuorossaPelaaja = view.findViewById(R.id.tvPelaajaNimi_Peli);

        alustaPelilauta();
        alustaPeli();

        return view;
        }

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

    private void asetaSeuraava() {
        tvVuorossaPelaaja.setText(pelaajat.get(vuorossaPelaaja).pelaajanimi);
        ivNappula.setImageDrawable(pelaajat.get(vuorossaPelaaja).pelaajakuva);
    }

    private void suoritaVuoro(int toiminto) {
        toiminto = 9;
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
            case 8 -> {
                fragmentManager.beginTransaction()
                        .replace(R.id.fcvMinipeliNakyma, Sanaselitys.class,null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
            }
            case 9 -> {
                fragmentManager.beginTransaction()
                        .replace(R.id.fcvMinipeliNakyma, FuckTheDealer.class,null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
            }
            case 10 -> {
                fragmentManager.beginTransaction()
                        .replace(R.id.fcvMinipeliNakyma, Ravit.class,null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
            }
        }
    }

    /**
     * Liikuttaa pelaajaa pelilaudalla
     * @param nopanHeitto Saa arvokseen nopanheiton tuloksen
     * @return Palauttaa saavutun ruudun tehtävän
     */
    private int liikutaPelaajaa(int nopanHeitto) {
        int uusiruutu = pelaajat.get(vuorossaPelaaja).sijainti + nopanHeitto;
        Pelaaja.liikutaPelaajaRuutuun(pelaajat.get(vuorossaPelaaja),peliRuudut.get(uusiruutu));
        return peliRuudut.get(uusiruutu).tehtava;
    }



    /**
     * Pelaajien ja pelin alustus
     */
    private void alustaPeli() {

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
        tvVuorossaPelaaja.setText(pelaajat.get(vuorossaPelaaja).pelaajanimi);
    }

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
                case 0 -> kortti.kuva = getResources().getDrawable(R.drawable.jokeri1,MainActivity.INSTANCE.getTheme());
                case 1 -> kortti.kuva = getResources().getDrawable(R.drawable.jokeri1,MainActivity.INSTANCE.getTheme());
                case 2 -> kortti.kuva = getResources().getDrawable(R.drawable.jokeri1,MainActivity.INSTANCE.getTheme());
                case 3 -> kortti.kuva = getResources().getDrawable(R.drawable.hertta4,MainActivity.INSTANCE.getTheme());
                case 4 -> kortti.kuva = getResources().getDrawable(R.drawable.hertta5,MainActivity.INSTANCE.getTheme());
                case 5 -> kortti.kuva = getResources().getDrawable(R.drawable.jokeri1,MainActivity.INSTANCE.getTheme());
                case 6 -> kortti.kuva = getResources().getDrawable(R.drawable.jokeri1,MainActivity.INSTANCE.getTheme());
                case 7 -> kortti.kuva = getResources().getDrawable(R.drawable.hertta8,MainActivity.INSTANCE.getTheme());
                case 8 -> kortti.kuva = getResources().getDrawable(R.drawable.hertta9,MainActivity.INSTANCE.getTheme());
                case 9 -> kortti.kuva = getResources().getDrawable(R.drawable.hertta10,MainActivity.INSTANCE.getTheme());
                case 10 -> kortti.kuva = getResources().getDrawable(R.drawable.hertta11,MainActivity.INSTANCE.getTheme());
                case 11 -> kortti.kuva = getResources().getDrawable(R.drawable.hertta12,MainActivity.INSTANCE.getTheme());
                case 12 -> kortti.kuva = getResources().getDrawable(R.drawable.jokeri1,MainActivity.INSTANCE.getTheme());
                case 13 -> kortti.kuva = getResources().getDrawable(R.drawable.jokeri1,MainActivity.INSTANCE.getTheme());
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
                case 25 -> kortti.kuva = getResources().getDrawable(R.drawable.jokeri1,MainActivity.INSTANCE.getTheme());
                case 26 -> kortti.kuva = getResources().getDrawable(R.drawable.jokeri1,MainActivity.INSTANCE.getTheme());
                case 27 -> kortti.kuva = getResources().getDrawable(R.drawable.jokeri1,MainActivity.INSTANCE.getTheme());
                case 28 -> kortti.kuva = getResources().getDrawable(R.drawable.jokeri1,MainActivity.INSTANCE.getTheme());
                case 29 -> kortti.kuva = getResources().getDrawable(R.drawable.pata4,MainActivity.INSTANCE.getTheme());
                case 30 -> kortti.kuva = getResources().getDrawable(R.drawable.pata5,MainActivity.INSTANCE.getTheme());
                case 31 -> kortti.kuva = getResources().getDrawable(R.drawable.pata6,MainActivity.INSTANCE.getTheme());
                case 32 -> kortti.kuva = getResources().getDrawable(R.drawable.jokeri1,MainActivity.INSTANCE.getTheme());
                case 33 -> kortti.kuva = getResources().getDrawable(R.drawable.pata8,MainActivity.INSTANCE.getTheme());
                case 34 -> kortti.kuva = getResources().getDrawable(R.drawable.pata9,MainActivity.INSTANCE.getTheme());
                case 35 -> kortti.kuva = getResources().getDrawable(R.drawable.pata10,MainActivity.INSTANCE.getTheme());
                case 36 -> kortti.kuva = getResources().getDrawable(R.drawable.pata11,MainActivity.INSTANCE.getTheme());
                case 37 -> kortti.kuva = getResources().getDrawable(R.drawable.pata12,MainActivity.INSTANCE.getTheme());
                case 38 -> kortti.kuva = getResources().getDrawable(R.drawable.jokeri2,MainActivity.INSTANCE.getTheme());
                case 39 -> kortti.kuva = getResources().getDrawable(R.drawable.jokeri2,MainActivity.INSTANCE.getTheme());
                case 40 -> kortti.kuva = getResources().getDrawable(R.drawable.jokeri2,MainActivity.INSTANCE.getTheme());
                case 41 -> kortti.kuva = getResources().getDrawable(R.drawable.risti3,MainActivity.INSTANCE.getTheme());
                case 42 -> kortti.kuva = getResources().getDrawable(R.drawable.risti4,MainActivity.INSTANCE.getTheme());
                case 43 -> kortti.kuva = getResources().getDrawable(R.drawable.risti5,MainActivity.INSTANCE.getTheme());
                case 44 -> kortti.kuva = getResources().getDrawable(R.drawable.risti6,MainActivity.INSTANCE.getTheme());
                case 45 -> kortti.kuva = getResources().getDrawable(R.drawable.risti7,MainActivity.INSTANCE.getTheme());
                case 46 -> kortti.kuva = getResources().getDrawable(R.drawable.risti8,MainActivity.INSTANCE.getTheme());
                case 47 -> kortti.kuva = getResources().getDrawable(R.drawable.risti9,MainActivity.INSTANCE.getTheme());
                case 48 -> kortti.kuva = getResources().getDrawable(R.drawable.ruutu10,MainActivity.INSTANCE.getTheme());
                case 49 -> kortti.kuva = getResources().getDrawable(R.drawable.risti11,MainActivity.INSTANCE.getTheme());
                case 50 -> kortti.kuva = getResources().getDrawable(R.drawable.risti12,MainActivity.INSTANCE.getTheme());
                case 51 -> kortti.kuva = getResources().getDrawable(R.drawable.jokeri2,MainActivity.INSTANCE.getTheme());
                //ETC. TEE OIKEILLA KORTEILLA
            }
            pakka.add(kortti);
        }
    }

    /**
     * Asetetaan pelilaudan ruuduille ominaisuudet
     */
    private void alustaPelilauta() {
        int pelilaudanKoko = 62;
        for (int i = 0; i < pelilaudanKoko; i++) {
            Ruutu ruutu = new Ruutu();
            Sijainti sijainti = new Sijainti();
            switch (i) {
                case 0 -> {
                    sijainti.x = 100f;
                    sijainti.y = 100f;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 0;
                    ruutu.ruudunNumero = 0;
                }
                case 1 -> {
                    sijainti.x = 20f;
                    sijainti.y = 30f;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 4;
                    ruutu.ruudunNumero = 1;
                }
                case 2 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 8;
                    ruutu.ruudunNumero = 2;
                }
                case 3 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 1;
                    ruutu.ruudunNumero = 3;
                }
                case 4 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 6;
                    ruutu.ruudunNumero = 4;
                }
                case 5 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 3;
                    ruutu.ruudunNumero = 5;
                }
                case 6 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 9;
                    ruutu.ruudunNumero = 6;
                }
                case 7 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 4;
                    ruutu.ruudunNumero = 7;
                }
                case 8 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 10;
                    ruutu.ruudunNumero = 8;
                }
                case 9 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 8;
                    ruutu.ruudunNumero = 9;
                }
                case 10 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 2;
                    ruutu.ruudunNumero = 10;
                }
                case 11 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 11;
                    ruutu.ruudunNumero = 11;
                    ruutu.lisaSiirrot = -5;
                }
                case 12 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 5;
                    ruutu.ruudunNumero = 12;
                }
                case 13 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 6;
                    ruutu.ruudunNumero = 13;
                }
                case 14 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 3;
                    ruutu.ruudunNumero = 14;
                }
                case 15 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 7;
                    ruutu.ruudunNumero = 15;
                    ruutu.lisaSiirrot = 3;
                }
                case 16 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 4;
                    ruutu.ruudunNumero = 16;
                }
                case 17 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 6;
                    ruutu.ruudunNumero = 17;
                }
                case 18 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 1;
                    ruutu.ruudunNumero = 18;
                }
                case 19 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 8;
                    ruutu.ruudunNumero = 19;
                }
                case 20 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 5;
                    ruutu.ruudunNumero = 20;
                }
                case 21 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 10;
                    ruutu.ruudunNumero = 21;
                }
                case 22 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 6;
                    ruutu.ruudunNumero = 22;
                }
                case 23 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 9;
                    ruutu.ruudunNumero = 23;
                }
                case 24 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 3;
                    ruutu.ruudunNumero = 24;
                }
                case 25 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 7;
                    ruutu.ruudunNumero = 25;
                    ruutu.lisaSiirrot = 3;
                }
                case 26 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 8;
                    ruutu.ruudunNumero = 26;
                }
                case 27 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 1;
                    ruutu.ruudunNumero = 27;
                }
                case 28 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 5;
                    ruutu.ruudunNumero = 28;
                }
                case 29 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 10;
                    ruutu.ruudunNumero = 29;
                }
                case 30 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 2;
                    ruutu.ruudunNumero = 30;
                }
                case 31 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 9;
                    ruutu.ruudunNumero = 31;
                }
                case 32 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 3;
                    ruutu.ruudunNumero = 32;
                }
                case 33 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 10;
                    ruutu.ruudunNumero = 33;
                }
                case 34 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 1;
                    ruutu.ruudunNumero = 34;
                }
                case 35 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 6;
                    ruutu.ruudunNumero = 35;
                }
                case 36 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 4;
                    ruutu.ruudunNumero = 36;
                }
                case 37 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 5;
                    ruutu.ruudunNumero = 37;
                }
                case 38 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 3;
                    ruutu.ruudunNumero = 38;
                }
                case 39 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 11;
                    ruutu.ruudunNumero = 39;
                    ruutu.lisaSiirrot = -4;
                }
                case 40 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 9;
                    ruutu.ruudunNumero = 40;
                }
                case 41 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 6;
                    ruutu.ruudunNumero = 41;
                }
                case 42 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 10;
                    ruutu.ruudunNumero = 42;
                }
                case 43 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 1;
                    ruutu.ruudunNumero = 43;
                }
                case 44 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 4;
                    ruutu.ruudunNumero = 44;
                }
                case 45 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 9;
                    ruutu.ruudunNumero = 45;
                }
                case 46 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 8;
                    ruutu.ruudunNumero = 46;
                }
                case 47 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 4;
                    ruutu.ruudunNumero = 47;
                }
                case 48 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 11;
                    ruutu.ruudunNumero = 48;
                    ruutu.lisaSiirrot = -5;
                }
                case 49 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 3;
                    ruutu.ruudunNumero = 49;
                }
                case 50 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 9;
                    ruutu.ruudunNumero = 50;
                }
                case 51 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 2;
                    ruutu.ruudunNumero = 51;
                }
                case 52 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 11;
                    ruutu.ruudunNumero = 52;
                    ruutu.lisaSiirrot = -5;
                }
                case 53 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 5;
                    ruutu.ruudunNumero = 53;
                }
                case 54 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 8;
                    ruutu.ruudunNumero = 54;
                }
                case 55 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 4;
                    ruutu.ruudunNumero = 55;
                }
                case 56 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 12;
                    ruutu.ruudunNumero = 56;
                    ruutu.lisaSiirrot = -18;
                }
                case 57 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 7;
                    ruutu.ruudunNumero = 57;
                    ruutu.lisaSiirrot = 3;
                }
                case 58 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 5;
                    ruutu.ruudunNumero = 58;
                }
                case 59 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 1;
                    ruutu.ruudunNumero = 59;
                }
                case 60 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 3;
                    ruutu.ruudunNumero = 60;
                }
                case 61 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 13;
                    ruutu.ruudunNumero = 61;
                }
            }
            peliRuudut.add(ruutu);
        }
    }
}