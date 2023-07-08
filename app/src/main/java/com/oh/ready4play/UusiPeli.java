package com.oh.ready4play;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.oh.ready4play.adapterit_ja_itemit.PelaajaAdapteri;

import java.util.ArrayList;

//TODO: Jäänyt bugi. Jos poistaa viimeisen, sen nappula tulee uusiksi. Muuten toimii

/**
 * Uuden pelin asettamisen näkymä
 * @version 1.0
 * @author Olli Hilke
 */
public class UusiPeli extends Fragment {

    @SuppressLint("StaticFieldLeak")
    public static UusiPeli INSTANCE;
    /**
     * Pelaajalista joka esitetään recycle viewissä ja josta haetaan pelaajat peliin.
     */
    public static ArrayList<Pelaaja> itemArrayList = new ArrayList<>();
    /**
     * Taulukko, johon tallennetaan tieto onko nappula varattu
     */
    public static Boolean[] nappulaKuva = new Boolean[10];
    /**
     * Seuraavaksi lisättävän pelaajan nappulan imageView
     */
    public ImageView ivNappulanKuva;
    /**
     * Pelaajien lukumäärä
     */
    public static int pelaajaMaara = 0;
    /**
     * Painike pelaajan lisäämistä varten
     */
    public Button btLisaaPelaaja;
    /**
     * Painike pelin aloittamiseen
     */
    public Button btAloitaPeli;
    /**
     * Boolean kertomaan onko 10 pelaajaa jo valittu
     */
    public static boolean taynna = false;

    public UusiPeli() {
        super(R.layout.fragment_uusi_peli);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_uusi_peli, container, false);
        INSTANCE = this;

        //Asetetaan kaikki nappulat saataville
        alustaNappulaKuvaValitsin();

        btAloitaPeli = view.findViewById(R.id.btAloitaPeli_UusiPeli);
        btAloitaPeli.setEnabled(false);
        btLisaaPelaaja = view.findViewById(R.id.btLisaaPelaaja_UusiPeli);
        EditText etLisattavaPelaajaNimi = view.findViewById(R.id.etLisattavaPelaaja_UusiPeli);
        ivNappulanKuva = view.findViewById(R.id.ivLisattavanKuva_UusiPeli);

        ivNappulanKuva.setImageResource(R.drawable.nappula_kulta);

        RecyclerView recyclerView = view.findViewById(R.id.rvPelaajat);

        recyclerView.setAdapter(new PelaajaAdapteri(itemArrayList));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));


        btAloitaPeli.setOnClickListener(e -> {
            Navigation.findNavController(view).navigate(R.id.action_uusiPeli_to_peli);
        });

        btLisaaPelaaja.setOnClickListener(e -> {
            pelaajaMaara ++;
            if (pelaajaMaara >= 2) {
                btAloitaPeli.setEnabled(true);
            }
            if (!taynna) {
                if (!etLisattavaPelaajaNimi.getText().toString().equals("") && etLisattavaPelaajaNimi.getText().toString().length() < 16) {
                    Pelaaja pelaaja = new Pelaaja(vapaaNappula(), etLisattavaPelaajaNimi.getText().toString(), valitseNappula(vapaaNappula()));
                    if (etLisattavaPelaajaNimi.getText().toString().equals("OH")) {
                        pelaaja.pelaajakuva = ResourcesCompat.getDrawable(getResources(),R.drawable.nappula_led_vihrea,null);
                    }
                    itemArrayList.add(pelaaja);
                    etLisattavaPelaajaNimi.setText("");
                } else {
                    if (etLisattavaPelaajaNimi.getText().toString().length()>0) {
                        Toast.makeText(INSTANCE.getContext(), R.string.text_tooLongName, Toast.LENGTH_SHORT).show();
                    } else {
                    Toast.makeText(INSTANCE.getContext(), R.string.text_giveName,Toast.LENGTH_SHORT).show();
                    }
                }
                recyclerView.setAdapter(new PelaajaAdapteri(itemArrayList));
            } else {btLisaaPelaaja.setEnabled(false);}
        });

        return view;
    }

    private void alustaNappulaKuvaValitsin() {
        for (int i = 0; i < 10; i++) {
            nappulaKuva[i] = true;
        }
    }

    /**
     * Tarkistaa listasta pelaajalle indeksinumeron
     * @return palauttaa vapaan indeksinumeron 0-9
     */
    private int vapaaNappula() {
        taynna = true;
        int vapaaNappula = 0;
        for (int i = 0; i < 10; i++) {
            if (!nappulaKuva[i]) {
                vapaaNappula++;
            } else {
                taynna = false;
                btLisaaPelaaja.setEnabled(true);
                break;
            }
        }
        return vapaaNappula;
    }

    /**
     * Palauttaa vapaana olevan nappulan kuvan
     * @param vapaaNappula Vapaana oleva nappula
     * @return Palauttaa nappulan kuvan
     */
    private int seuraavaNappula(int vapaaNappula) {
        switch (vapaaNappula) {
            case 0 -> {return R.drawable.nappula_kulta;}
            case 1 -> {return R.drawable.nappula_valkoinen_marmori;}
            case 2 -> {return R.drawable.nappula_jade;}
            case 3 -> {return R.drawable.nappula_punainen_emali;}
            case 4 -> {return R.drawable.nappula_safiiri;}
            case 5 -> {return R.drawable.nappula_ruby;}
            case 6 -> {return R.drawable.nappula_carbon;}
            case 7 -> {return R.drawable.nappula_keltainen_emali;}
            case 8 -> {return R.drawable.nappula_peili;}
            case 9 -> {return R.drawable.nappula_sininen_emali;}
            case 10 -> {return R.drawable.block;}
        }
        return R.drawable.block;
    }

    /**
     * Valitsee nappulan
     * @param vapaaNappula Saa vapaana olevan nappulan indeksinumeron
     * @return Palauttaa uudelle pelaajalle vapaan nappulan kuvan
     */
    private Drawable valitseNappula(int vapaaNappula) {
        Drawable drawable = ivNappulanKuva.getDrawable();
        switch (vapaaNappula) {
            case 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 -> {
                nappulaKuva[vapaaNappula] = false;
                btLisaaPelaaja.setEnabled(true);
            }
            case 10 -> {
                ivNappulanKuva.setImageResource(R.drawable.block);
                taynna = true;
            }
        }
        ivNappulanKuva.setImageResource(seuraavaNappula(vapaaNappula()));
        return drawable;
    }
}