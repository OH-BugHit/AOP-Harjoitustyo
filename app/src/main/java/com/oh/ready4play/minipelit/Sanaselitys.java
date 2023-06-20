package com.oh.ready4play.minipelit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.oh.ready4play.MainActivity;
import com.oh.ready4play.Peli;
import com.oh.ready4play.R;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class Sanaselitys extends Fragment {
    private final Random random = new Random();
    ArrayList<String> sanasto = new ArrayList<>();
    ArrayList<String> glossary = new ArrayList<>();
    Thread t1;
    Button btOhita;
    Button btOikein;
    private TextView tvAika;
    private TextView tvPistenaytto;
    private TextView tvSana;
    private TextView tvSanaOhjeistus;
    private int aika = 5;
    private double pistemaara = 0;
    volatile boolean sanapeliOhi = false;

    public Sanaselitys() {super(R.layout.fragment_sanaselitys);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sanaselitys, container, false);

        teeSanaluettelo();

        tvSanaOhjeistus = view.findViewById(R.id.tvOhjeistus_Sanaselitys);
        tvSana = view.findViewById(R.id.tvSana_Sanaselitys);
        tvPistenaytto = view.findViewById(R.id.tvPisteNaytto_Sanaselitys);
        tvPistenaytto.setVisibility(View.INVISIBLE);
        tvAika = view.findViewById(R.id.tvAika_Sanaselitys);

        Button btAloita = view.findViewById(R.id.btAloita_Sanaselitys);
        btOhita = view.findViewById(R.id.btOhita_Sanaselitys);
        btOikein = view.findViewById(R.id.btOikein_Sanaselitys);
        Button btJatkaPelia = view.findViewById(R.id.btJatkaPelia_Sanaselitys);
        btJatkaPelia.setVisibility(View.INVISIBLE);

        btOhita.setVisibility(View.INVISIBLE);
        btOikein.setVisibility(View.INVISIBLE);


        btOikein.setOnClickListener(e -> {
            pistemaara += 1;
            tvPistenaytto.setText(String.valueOf(pistemaara));
            etenePelissa(btOhita, btOikein, btJatkaPelia);
        });

        btOhita.setOnClickListener(e -> {
            pistemaara -= 0.5;
            tvPistenaytto.setText(String.valueOf(pistemaara));
            etenePelissa(btOhita, btOikein, btJatkaPelia);
        });

        btAloita.setOnClickListener(e -> {
            tvSanaOhjeistus.setVisibility(View.INVISIBLE);
            tvPistenaytto.setVisibility(View.VISIBLE);
            tvPistenaytto.setText("0");
            btAloita.setVisibility(View.INVISIBLE);
            btOhita.setVisibility(View.VISIBLE);
            btOikein.setVisibility(View.VISIBLE);
            tvAika.setVisibility(View.VISIBLE);
            kaynnistaAjastin();
            seuraavaSana();
        });


        btJatkaPelia.setOnClickListener(e -> {
            //tvSana.setText(R.string.text_taskDescriptionDictionary);
            //tvPistenaytto.setText("");
            t1.interrupt();
            Peli.seuraavaVuoro = true;
            Peli.fragmentManager.beginTransaction()
                    .remove(this)
                    .setReorderingAllowed(true)
                    .commit();
        });

        return view;
    }

    private void etenePelissa(Button btOhita, Button btOikein, Button btJatkaPelia) {
        if (sanapeliOhi) {
            btOikein.setVisibility(View.INVISIBLE);
            btOhita.setVisibility(View.INVISIBLE);
            int loppupisteet = (int) Math.floor(pistemaara);
            tvSana.setVisibility(View.INVISIBLE);
            tvSanaOhjeistus.setVisibility(View.VISIBLE);
            tvSanaOhjeistus.setText(R.string.text_lopputekstiDictionary);
            btJatkaPelia.setVisibility(View.VISIBLE);
        } else {
            seuraavaSana();
        }
    }

    private void kaynnistaAjastin() {
        t1 = new Thread(() -> {
            while (aika > 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                MainActivity.INSTANCE.runOnUiThread(Sanaselitys.this::paivitaAika);
            }
            tvAika.setVisibility(View.INVISIBLE);
            sanapeliOhi = true;
        });
        t1.start();
    }

    private void paivitaAika() {
        aika -= 1;
        tvAika.setText(String.valueOf(aika));
    }

    private void seuraavaSana() {
        String kieli = Locale.getDefault().getISO3Language();
        System.out.println("Valittu kieli on: " + kieli);
        String valittuSana;
        if (!sanasto.isEmpty() || !glossary.isEmpty()) {
            switch (kieli) {
                case "fin" -> {
                    valittuSana = sanasto.remove(random.nextInt(sanasto.size()));
                    tvSana.setText(valittuSana);
                }
                default -> {
                    valittuSana = glossary.remove(random.nextInt(glossary.size()));
                    tvSana.setText(valittuSana);
                }
            }
        } else tvSana.setText(R.string.text_blank);
    }
    private void teeSanaluettelo() {
        sanasto.add("Margariini");
        sanasto.add("Tomaatti");
        sanasto.add("Lasi");
        sanasto.add("Koira");
        sanasto.add("Puu");
        sanasto.add("Pöytä");
        sanasto.add("Kirja");
        sanasto.add("Auto");
        sanasto.add("Matka");
        sanasto.add("Talo");
        sanasto.add("Kukka");
        sanasto.add("Kynä");
        sanasto.add("Valo");
        sanasto.add("Tuoli");
        sanasto.add("Ravintola");
        sanasto.add("Penkki");
        sanasto.add("Sänky");
        sanasto.add("Lautanen");
        sanasto.add("Kahvi");
        sanasto.add("Ruoka");
        sanasto.add("Viini");
        sanasto.add("Vesi");
        sanasto.add("Suklaa");
        sanasto.add("Musiikki");
        sanasto.add("Puhelin");
        sanasto.add("Paita");
        sanasto.add("Hame");
        sanasto.add("Saapas");
        sanasto.add("Koru");
        sanasto.add("Kamera");
        sanasto.add("Hammasharja");
        sanasto.add("Laukku");
        sanasto.add("Lompakko");
        sanasto.add("Avain");
        sanasto.add("Kello");
        sanasto.add("Puhelin");
        sanasto.add("Televisio");
        sanasto.add("Tietokone");
        sanasto.add("Laukku");
        sanasto.add("Lompakko");
        sanasto.add("Avain");
        sanasto.add("Kello");
        sanasto.add("Puhelin");
        sanasto.add("Televisio");
        sanasto.add("Tietokone");
        sanasto.add("Silmälasit");
        sanasto.add("Sadehattu");
        sanasto.add("Koru");
        sanasto.add("Laukku");
        sanasto.add("Lompakko");
        sanasto.add("Avain");
        sanasto.add("Kello");
        sanasto.add("Puhelin");
        sanasto.add("Televisio");
        sanasto.add("Tietokone");
        sanasto.add("Silmälasit");
        sanasto.add("Sadehattu");
        sanasto.add("Mekko");
        sanasto.add("Takki");
        sanasto.add("Pusero");
        sanasto.add("Sukat");
        sanasto.add("Käsineet");
        sanasto.add("Vyö");
        sanasto.add("Kaulahuivi");
        sanasto.add("Korvakorut");
        sanasto.add("Rannekoru");
        sanasto.add("Kaulakoru");
        sanasto.add("Kampa");
        sanasto.add("Harja");
        sanasto.add("Shampoo");
        sanasto.add("Hoitoaine");
        sanasto.add("Saippua");
        sanasto.add("Pesuaine");
        sanasto.add("Hammastahna");
        sanasto.add("Partakone");
        sanasto.add("Partavaahto");
        sanasto.add("Imuri");
        sanasto.add("Moppi");
        sanasto.add("Luuta");
        sanasto.add("Siivousliina");
        sanasto.add("Roskakori");
        sanasto.add("Imuri");
        sanasto.add("Pesukone");
        sanasto.add("Kuivausrumpu");
        sanasto.add("Pyyhe");
        sanasto.add("Koriste");
        sanasto.add("Maalaus");
        sanasto.add("Puutarha");
        sanasto.add("Liina");
        sanasto.add("Matto");
        sanasto.add("Kuva");
        sanasto.add("Lehti");
        sanasto.add("Uutinen");
        sanasto.add("Kirjoitus");
        sanasto.add("Toimittaja");
        sanasto.add("Haastattelu");
        sanasto.add("Lääkäri");
        sanasto.add("Hoitaja");
        sanasto.add("Potilas");
        sanasto.add("Sairaala");
        sanasto.add("Klinikka");
        sanasto.add("Lääke");
        sanasto.add("Resepti");
        sanasto.add("Terveys");
        sanasto.add("Liikunta");
        sanasto.add("Ravitsemus");
        sanasto.add("Hedelmä");
        sanasto.add("Vihannes");
        sanasto.add("Jooga");
        sanasto.add("Meditaatio");
        sanasto.add("Hieronta");
        sanasto.add("Terapia");
        sanasto.add("Kellari");
        sanasto.add("Olohuone");
        sanasto.add("Keittiö");
        sanasto.add("Makuuhuone");
        sanasto.add("Kylpyhuone");
        sanasto.add("Piha");
        sanasto.add("Portaat");
        sanasto.add("Parveke");
        sanasto.add("Takapiha");
        sanasto.add("Terassi");
        sanasto.add("Alkoholi");
        sanasto.add("Olut");
        sanasto.add("Viini");
        sanasto.add("Viinilasi");
        sanasto.add("Viski");
        sanasto.add("Viinipullo");
        sanasto.add("Samppanja");
        sanasto.add("Brandy");
        sanasto.add("Rommi");
        sanasto.add("Tequila");
        sanasto.add("Likööri");
        sanasto.add("Vodka");
        sanasto.add("Gini");
        sanasto.add("Oluttölkki");
        sanasto.add("Viinitynnyri");
        sanasto.add("Viinilasi");
        sanasto.add("Viinipullonavaaja");
        sanasto.add("Viskipullo");
        sanasto.add("Viskikarahvi");
        sanasto.add("Viinipunaviinilasi");
        sanasto.add("Valkoviinilasi");
        sanasto.add("Viinilasi");
        sanasto.add("Viinipullonavaaja");
        sanasto.add("Samppanjapullo");
        sanasto.add("Samppanjapullonavaaja");
        sanasto.add("Brandytuoppi");
        sanasto.add("Brändilasi");
        sanasto.add("Rommituoppi");
        sanasto.add("Tequilalasi");
        sanasto.add("Liköörituoppi");
        sanasto.add("Vodkamuki");
        sanasto.add("Ginituoppi");
        sanasto.add("Alkoholiton juoma");
        sanasto.add("Juhlat");
        sanasto.add("Baaritiski");
        sanasto.add("Viinibaari");
        sanasto.add("Olutravintola");
        sanasto.add("Cocktailbaari");
        sanasto.add("Sekoitusjuoma");
        sanasto.add("Juomasekoitus");

        //Lisätään englannikieliset sanat

        glossary.add("Chair");
        glossary.add("Desk");
        glossary.add("Lamp");
        glossary.add("Rug");
        glossary.add("Couch");
        glossary.add("Mirror");
        glossary.add("Plant");
        glossary.add("Window");
        glossary.add("Door");
        glossary.add("Painting");
        glossary.add("Clock");
        glossary.add("Knife");
        glossary.add("Spoon");
        glossary.add("Fork");
        glossary.add("Plate");
        glossary.add("Cup");
        glossary.add("Bowl");
        glossary.add("Napkin");
        glossary.add("Tablecloth");
        glossary.add("Bookshelf");
        glossary.add("Bookcase");
        glossary.add("Laptop");
        glossary.add("Keyboard");
        glossary.add("Mouse");
        glossary.add("Monitor");
        glossary.add("Printer");
        glossary.add("Headphones");
        glossary.add("Speaker");
        glossary.add("Guitar");
        glossary.add("Piano");
        glossary.add("Drums");
        glossary.add("Microphone");
        glossary.add("Concert");
        glossary.add("Movie");
        glossary.add("Play");
        glossary.add("Dance");
        glossary.add("Party");
        glossary.add("Wedding");
        glossary.add("Birthday");
        glossary.add("Gift");
        glossary.add("Paint");
        glossary.add("Canvas");
        glossary.add("Brush");
        glossary.add("Easel");
        glossary.add("Camera");
        glossary.add("Photograph");
        glossary.add("Film");
        glossary.add("Chair");
        glossary.add("Desk");
        glossary.add("Lamp");
        glossary.add("Rug");
        glossary.add("Couch");
        glossary.add("Mirror");
        glossary.add("Plant");
        glossary.add("Window");
        glossary.add("Door");
        glossary.add("Painting");
        glossary.add("Clock");
        glossary.add("Knife");
        glossary.add("Spoon");
        glossary.add("Fork");
        glossary.add("Plate");
        glossary.add("Cup");
        glossary.add("Bowl");
        glossary.add("Napkin");
        glossary.add("Tablecloth");
        glossary.add("Bookshelf");
        glossary.add("Bookcase");
        glossary.add("Laptop");
        glossary.add("Keyboard");
        glossary.add("Mouse");
        glossary.add("Monitor");
        glossary.add("Printer");
        glossary.add("Headphones");
        glossary.add("Speaker");
        glossary.add("Guitar");
        glossary.add("Piano");
        glossary.add("Drums");
        glossary.add("Microphone");
        glossary.add("Concert");
        glossary.add("Movie");
        glossary.add("Play");
        glossary.add("Dance");
        glossary.add("Party");
        glossary.add("Wedding");
        glossary.add("Birthday");
        glossary.add("Gift");
        glossary.add("Paint");
        glossary.add("Canvas");
        glossary.add("Brush");
        glossary.add("Easel");
        glossary.add("Camera");
        glossary.add("Photograph");
        glossary.add("Film");
        glossary.add("Dog");
        glossary.add("Tree");
        glossary.add("Table");
        glossary.add("Book");
        glossary.add("Car");
        glossary.add("Trip");
        glossary.add("House");
        glossary.add("Flower");
        glossary.add("Pen");
        glossary.add("Light");
        glossary.add("Chair");
        glossary.add("Restaurant");
        glossary.add("Bench");
        glossary.add("Bed");
        glossary.add("Plate");
        glossary.add("Coffee");
        glossary.add("Food");
        glossary.add("Wine");
        glossary.add("Water");
        glossary.add("Chocolate");
        glossary.add("Music");
        glossary.add("Phone");
        glossary.add("Shirt");
        glossary.add("Skirt");
        glossary.add("Boot");
        glossary.add("Jewelry");
        glossary.add("Camera");
        glossary.add("Toothbrush");
        glossary.add("Bag");
        glossary.add("Wallet");
        glossary.add("Key");
        glossary.add("Clock");
        glossary.add("Phone");
        glossary.add("Television");
        glossary.add("Computer");
        glossary.add("Glasses");
        glossary.add("Sunhat");
        glossary.add("Jewelry");
        glossary.add("Bag");
        glossary.add("Wallet");
        glossary.add("Key");
        glossary.add("Clock");
        glossary.add("Phone");
        glossary.add("Television");
        glossary.add("Computer");
        glossary.add("Glasses");
        glossary.add("Sunhat");
        glossary.add("Dress");
        glossary.add("Jacket");
        glossary.add("Blouse");
        glossary.add("Socks");
        glossary.add("Gloves");
        glossary.add("Belt");
        glossary.add("Scarf");
        glossary.add("Earrings");
        glossary.add("Bracelet");
        glossary.add("Necklace");
        glossary.add("Comb");
        glossary.add("Brush");
        glossary.add("Shampoo");
        glossary.add("Conditioner");
        glossary.add("Soap");
        glossary.add("Detergent");
        glossary.add("Toothpaste");
        glossary.add("Razor");
        glossary.add("Shaving foam");
        glossary.add("Vacuum cleaner");
        glossary.add("Mop");
        glossary.add("Broom");
        glossary.add("Cleaning cloth");
        glossary.add("Trash can");
        glossary.add("Vacuum cleaner");
        glossary.add("Washing machine");
        glossary.add("Dryer");
        glossary.add("Towel");
        glossary.add("Decoration");
        glossary.add("Painting");
        glossary.add("Garden");
        glossary.add("Tablecloth");
        glossary.add("Carpet");
        glossary.add("Picture");
        glossary.add("Magazine");
        glossary.add("News");
        glossary.add("Writing");
        glossary.add("Journalist");
        glossary.add("Interview");
        glossary.add("Doctor");
        glossary.add("Nurse");
        glossary.add("Patient");
        glossary.add("Hospital");
        glossary.add("Clinic");
        glossary.add("Medicine");
        glossary.add("Prescription");
        glossary.add("Health");
        glossary.add("Exercise");
        glossary.add("Nutrition");
        glossary.add("Fruit");
        glossary.add("Vegetable");
        glossary.add("Yoga");
        glossary.add("Meditation");
        glossary.add("Massage");
        glossary.add("Therapy");
        glossary.add("Basement");
        glossary.add("Living room");
        glossary.add("Kitchen");
        glossary.add("Bedroom");
        glossary.add("Bathroom");
        glossary.add("Yard");
        glossary.add("Stairs");
        glossary.add("Balcony");
        glossary.add("Backyard");
        glossary.add("Terrace");
        glossary.add("Alcohol");
        glossary.add("Beer");
        glossary.add("Wine");
        glossary.add("Wine glass");
        glossary.add("Whiskey");
        glossary.add("Wine bottle");
        glossary.add("Champagne");
        glossary.add("Brandy");
        glossary.add("Rum");
        glossary.add("Tequila");
        glossary.add("Liqueur");
        glossary.add("Vodka");
        glossary.add("Gin");
        glossary.add("Beer can");
        glossary.add("Wine barrel");
        glossary.add("Wine glass");
        glossary.add("Corkscrew");
        glossary.add("Whiskey bottle");
        glossary.add("Whiskey decanter");
        glossary.add("Red wine glass");
        glossary.add("White wine glass");
        glossary.add("Wine glass");
        glossary.add("Corkscrew");
        glossary.add("Champagne bottle");
        glossary.add("Champagne opener");
        glossary.add("Brandy snifter");
        glossary.add("Brandy glass");
        glossary.add("Rum tumbler");
        glossary.add("Tequila glass");
        glossary.add("Liqueur glass");
        glossary.add("Vodka mug");
        glossary.add("Gin glass");
        glossary.add("Non-alcoholic drink");
        glossary.add("Celebration");
        glossary.add("Bar counter");
        glossary.add("Wine bar");
        glossary.add("Pub");
        glossary.add("Cocktail bar");
        glossary.add("Mixed drink");
        glossary.add("Drink mix");
    }
}