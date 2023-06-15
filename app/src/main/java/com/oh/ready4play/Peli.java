package com.oh.ready4play;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.oh.ready4play.minipelit.Hitler;

import java.util.ArrayList;

public class Peli extends Fragment {
    public static volatile boolean seuraavaVuoro = false;
    View view;
    public static ArrayList<Pelaaja> pelaajat;
    private static ArrayList<Ruutu> peliRuudut = new ArrayList<>();;
    public static int vuorossaPelaaja = 0;
    public static int pelaajamaara;
    private int toiminto;
    private boolean hampuriKlikattu = false;
    static Button btHeitaNoppa;
    static Button btOhita;
    ImageView ivNappula;
    TextView tvVuorossaPelaaja;
    static TextView tvOhitaPelaajanimi;
    public static FragmentManager fragmentManager;
    public Peli() {
        // Required empty public constructor
    }
    public static void naytaNapit() {
        btOhita.setVisibility(View.VISIBLE);
        btHeitaNoppa.setVisibility(View.VISIBLE);
        tvOhitaPelaajanimi.setEnabled(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_peli, container, false);

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

        btHeitaNoppa = view.findViewById(R.id.btHeita_Peli);
        btOhita = view.findViewById(R.id.btOhita_Peli);

        ImageView ivHampuri = view.findViewById(R.id.ivHampuri_peli);
        btHeitaNoppa.setOnClickListener(e -> {

            btOhita.setVisibility(View.INVISIBLE);
            btHeitaNoppa.setVisibility(View.INVISIBLE);
            tvOhitaPelaajanimi.setEnabled(false);

            int nopanHeitto = Noppa.heitaNoppaa();
            toiminto = liikutaPelaajaa(nopanHeitto);
            if (toiminto != 13) {
                suoritaVuoro(toiminto);
            } else {//TODO: GAME OVER! Voittaja on vuorossaoleva
            }
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    seuraavanPelaajanVuoro();
                    MainActivity.INSTANCE.runOnUiThread(Peli::naytaNapit);
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

        //TODO: Mieti kannattaako toteuttaa erillisen threadiin peli pyörimään vai ei. Jos toteutat niin tee onDestroy ja threadien tappamiset jne...
        alustaPelilauta();
        alustaPeli();
        //aloita peliin toteutetaan sitten se thread homma jos toteutetaan. Nyt toimii suoraan Heita noppaa napilla vaan.
        aloitaPeli();
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
        tvVuorossaPelaaja.setText(pelaajat.get(vuorossaPelaaja).pelaajanimi);
        ivNappula.setImageDrawable(pelaajat.get(vuorossaPelaaja).pelaajakuva);
    }

    private void suoritaVuoro(int toiminto) {
        /*
        switch (toiminto) {
            case 1 -> {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView,Hitler.class,null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
            }
        }

         */
        fragmentManager.beginTransaction()
                .replace(R.id.fcvMinipeliNakyma,Hitler.class,null)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
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
     * Asetetaan pelilaudan ruuduille ominaisuudet
     */
    private void alustaPelilauta() {
        int pelilaudanKoko = 62;
        for (int i = 0; i < pelilaudanKoko; i++) {
            Ruutu ruutu = new Ruutu();
            Sijainti sijainti = new Sijainti();
            switch (i) {
                case 0 -> {
                    sijainti.x = 100;
                    sijainti.y = 100;
                    ruutu.sijainti = sijainti;
                    ruutu.tehtava = 0;
                    ruutu.ruudunNumero = 0;
                }
                case 1 -> {
                    sijainti.x = 0;
                    sijainti.y = 0;
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

    /**
     * Pelaajien ja pelin alustus
     */
    private void alustaPeli() {
        vuorossaPelaaja = 0;
        pelaajamaara = pelaajat.size();
        for (Pelaaja pelaaja : pelaajat) {
            pelaaja.imageView = new ImageView(getContext());
            pelaaja.imageView.setClipToOutline(true);
            pelaaja.imageView.setImageDrawable(pelaaja.pelaajakuva);
            Pelaaja.liikutaPelaajaRuutuun(pelaaja, peliRuudut.get(0));
        }
        ivNappula.setImageDrawable(pelaajat.get(vuorossaPelaaja).pelaajakuva);
        tvVuorossaPelaaja.setText(pelaajat.get(vuorossaPelaaja).pelaajanimi);
    }

    private void aloitaPeli() {
    }
}