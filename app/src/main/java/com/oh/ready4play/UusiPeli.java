package com.oh.ready4play;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

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

import com.oh.ready4play.adapterit_ja_itemit.PelaajaAdapteri;

import java.util.ArrayList;

//TODO: Jäänyt bugi. Jos poistaa viimeisen, sen nappula tulee uusiksi. Muuten toimii
public class UusiPeli extends Fragment {
    public static Tallennus asetukset;
    public static ArrayList<Pelaaja> itemArrayList = new ArrayList<>();
    public static Boolean[] nappulaKuva = new Boolean[10];
    public static ImageView ivNappulanKuva;
    Button btLisaaPelaaja;
    public static boolean taynna = false;

    public UusiPeli() {
        super(R.layout.fragment_uusi_peli);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_uusi_peli, container, false);

        //Asetetaan kaikki nappulat saataville
        alustaNappulaKuvaValitsin();

        Button btAloitaPeli = view.findViewById(R.id.btAloitaPeli_UusiPeli);
        btLisaaPelaaja = view.findViewById(R.id.btLisaaPelaaja_UusiPeli);
        EditText etLisattavaPelaajaNimi = view.findViewById(R.id.etLisattavaPelaaja_UusiPeli);
        ivNappulanKuva = view.findViewById(R.id.ivLisattavanKuva_UusiPeli);

        ivNappulanKuva.setImageResource(R.drawable.nappula1);

        RecyclerView recyclerView = view.findViewById(R.id.rvPelaajat);

        recyclerView.setAdapter(new PelaajaAdapteri(itemArrayList));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));


        btAloitaPeli.setOnClickListener(e -> {
            Navigation.findNavController(view).navigate(R.id.action_uusiPeli_to_peli);
        });

        btLisaaPelaaja.setOnClickListener(e -> {
            if (!taynna) {
                if (!etLisattavaPelaajaNimi.getText().toString().equals("")) {
                    Pelaaja pelaaja = new Pelaaja(vapaaNappula(), etLisattavaPelaajaNimi.getText().toString(), valitseNappula(vapaaNappula(), true));
                    itemArrayList.add(pelaaja);
                    etLisattavaPelaajaNimi.setText("");
                } else {
                    System.out.println("Nimi puuttuu");
                    //TODO: Tee ilmoitus nimen puuttumisesta jos aikaa
                }
                recyclerView.setAdapter(new PelaajaAdapteri(itemArrayList));
            }
        });

        return view;
    }

    private void alustaNappulaKuvaValitsin() {
        for (int i = 0; i < 10; i++) {
            nappulaKuva[i] = true;
        }
    }

    private int vapaaNappula() {
        taynna = true;
        int vapaaNappula = 0;
        for (int i = 0; i < 10; i++) {
            if (!nappulaKuva[i]) {
                vapaaNappula++;
            } else {
                taynna = false;
                break;
            }
        }
        return vapaaNappula;
    }

    private int seuraavaNappula(int vapaaNappula) {
        switch (vapaaNappula) {
            case 0 -> {return R.drawable.nappula1;}
            case 1 -> {return R.drawable.nappula2;}
            case 2 -> {return R.drawable.nappula3;}
            case 3 -> {return R.drawable.nappula4;}
            case 4 -> {return R.drawable.nappula5;}
            case 5 -> {return R.drawable.nappula6;}
            case 6 -> {return R.drawable.nappula7;}
            case 7 -> {return R.drawable.nappula8;}
            case 8 -> {return R.drawable.nappula9;}
            case 9 -> {return R.drawable.nappula10;}
            case 10 -> {return R.drawable.einappulaa;}
        }
        return R.drawable.einappulaa;
    }

    private Drawable valitseNappula(int vapaaNappula, boolean varaa) {
        Drawable drawable = ivNappulanKuva.getDrawable();
        switch (vapaaNappula) {
            case 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 -> {
                if (varaa) nappulaKuva[vapaaNappula] = false;
            }
            case 10 -> {
                ivNappulanKuva.setImageResource(R.drawable.einappulaa);
                taynna = true;
            }
        }
        ivNappulanKuva.setImageResource(seuraavaNappula(vapaaNappula()));
        return drawable;
    }
}