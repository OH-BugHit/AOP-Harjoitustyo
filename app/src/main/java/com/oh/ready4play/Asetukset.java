package com.oh.ready4play;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.android.material.switchmaterial.SwitchMaterial;

/**
 * Pelin asetusten fragment
 * @version 1.0
 * @author Olli Hilke
 */
public class Asetukset extends Fragment {
    /**
     * Kytkimet käytössä olevien tehtävien asettamiseen
     */
    private final SwitchMaterial[] kytkimet = new SwitchMaterial[12];
    /**
     * Noppien valintojen radiobuttonit (samaa grouppia)
     */
    private final RadioButton[] nopat = new RadioButton[4];
    /**
     * Sanaselityspelin keston asettamista varten EditText
     */
    private EditText etSanariKesto;
    /**
     * Luokan parametriton alustaja
     */
    public Asetukset() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_asetukset, container, false);

        nopat[0] = view.findViewById(R.id.rbNoppa0);
        nopat[1] = view.findViewById(R.id.rbNoppa2);
        nopat[2] = view.findViewById(R.id.rbNoppa3);
        nopat[3] = view.findViewById(R.id.rbNoppa5);

        kytkimet[0] = view.findViewById(R.id.swTeht1_Asetukset);
        kytkimet[1] = view.findViewById(R.id.swTeht2_Asetukset);
        kytkimet[2] = view.findViewById(R.id.swTeht3_Asetukset);
        kytkimet[3] = view.findViewById(R.id.swTeht4_Asetukset);
        kytkimet[4] = view.findViewById(R.id.swTeht5_Asetukset);
        kytkimet[5] = view.findViewById(R.id.swTeht6_Asetukset);
        kytkimet[6] = view.findViewById(R.id.swTeht7_Asetukset);
        kytkimet[7] = view.findViewById(R.id.swTeht8_Asetukset);
        kytkimet[8] = view.findViewById(R.id.swTeht9_Asetukset);
        kytkimet[9] = view.findViewById(R.id.swTeht10_Asetukset);
        kytkimet[10] = view.findViewById(R.id.swTeht11_Asetukset);
        kytkimet[11] = view.findViewById(R.id.swKasa_Asetukset);

        etSanariKesto = view.findViewById(R.id.etSanariKesto_Asetukset);

        lataaAsetukset();

        Button btPaluu = view.findViewById(R.id.btPaluu_Asetukset);
        Button btVahvista = view.findViewById(R.id.btVahvista_Asetukset);



        btPaluu.setOnClickListener(e -> {
            Navigation.findNavController(view).navigate(AsetuksetDirections.actionAsetuksetToAlkuvalikko());
        });

        btVahvista.setOnClickListener(e -> {
            tallennaAsetukset();
            Navigation.findNavController(view).navigate(AsetuksetDirections.actionAsetuksetToAlkuvalikko());
        });

        return view;
    }

    /**
     * Aiemmin käytettyjen asetusten lataaminen ja asettaminen näkymään
     */
    private void lataaAsetukset() {
        Alkuvalikko.sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        int defalutNoppa = 0;
        int noppa = Alkuvalikko.sharedPref.getInt(getString(R.string.saved_dice), defalutNoppa);
        switch (noppa) {
            case 0 -> nopat[0].setChecked(true);
            case 2 -> nopat[1].setChecked(true);
            case 3 -> nopat[2].setChecked(true);
            case 5 -> nopat[3].setChecked(true);
        }
        kytkimet[0].setChecked(lataaKytkin(getString(R.string.saved_task_hitler)));
        kytkimet[1].setChecked(lataaKytkin(getString(R.string.saved_task_hoe)));
        kytkimet[2].setChecked(lataaKytkin(getString(R.string.saved_task_girlsvsboys)));
        kytkimet[3].setChecked(lataaKytkin(getString(R.string.saved_task_truthordare)));
        kytkimet[4].setChecked(lataaKytkin(getString(R.string.saved_task_wouldyourahter)));
        kytkimet[5].setChecked(lataaKytkin(getString(R.string.saved_task_3shots)));
        kytkimet[6].setChecked(lataaKytkin(getString(R.string.saved_task_2truths1lie)));
        kytkimet[7].setChecked(lataaKytkin(getString(R.string.saved_task_dictionary)));
        kytkimet[8].setChecked(lataaKytkin(getString(R.string.saved_task_fthedealer)));
        kytkimet[9].setChecked(lataaKytkin(getString(R.string.saved_task_horserace)));
        kytkimet[10].setChecked(lataaKytkin(getString(R.string.saved_task_busdriver)));
        kytkimet[11].setChecked(lataaKytkin(getString(R.string.saved_task_stack)));

        int defalutSanariKesto = 90;
        etSanariKesto.setText(String.valueOf(Alkuvalikko.sharedPref.getInt(getString(R.string.saved_durationDictionary),defalutSanariKesto)));
    }

    /**
     * Kytkimen asetuksen lataaminen
     * @param avain Avainarvo asetuksessa
     * @return Palauttaa TRUE, jos asetus käytössä (Defaultvalue = True)
     */
    private boolean lataaKytkin(String avain) {
        boolean taskKaytossa;
        boolean defaultValue = true;
        taskKaytossa = Alkuvalikko.sharedPref.getBoolean(avain,defaultValue);
        return taskKaytossa;
    }

    /**
     * Asetusten tallentaminen
     */
    private void tallennaAsetukset() {
        int noppa = 0;
        for (int i = 0; i < nopat.length; i++) {
            if (nopat[0].isChecked()) {
                noppa = 0;
            }
        }
        if (nopat[0].isChecked()) {
            noppa = 0;
        } else if (nopat[1].isChecked()) {
            noppa = 2;
        } else if (nopat[2].isChecked()) {
            noppa = 3;
        } else if (nopat[3].isChecked()) {
            noppa = 5;
        }

        Alkuvalikko.sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = Alkuvalikko.sharedPref.edit();
        editor.putInt(getString(R.string.saved_dice), noppa);
        editor.putInt(getString(R.string.saved_durationDictionary),Integer.parseInt(String.valueOf(etSanariKesto.getText())));
        editor.putBoolean(getString(R.string.saved_task_hitler),kytkimet[0].isChecked());
        editor.putBoolean(getString(R.string.saved_task_hoe),kytkimet[1].isChecked());
        editor.putBoolean(getString(R.string.saved_task_girlsvsboys),kytkimet[2].isChecked());
        editor.putBoolean(getString(R.string.saved_task_truthordare),kytkimet[3].isChecked());
        editor.putBoolean(getString(R.string.saved_task_wouldyourahter),kytkimet[4].isChecked());
        editor.putBoolean(getString(R.string.saved_task_3shots),kytkimet[5].isChecked());
        editor.putBoolean(getString(R.string.saved_task_2truths1lie),kytkimet[6].isChecked());
        editor.putBoolean(getString(R.string.saved_task_dictionary),kytkimet[7].isChecked());
        editor.putBoolean(getString(R.string.saved_task_fthedealer),kytkimet[8].isChecked());
        editor.putBoolean(getString(R.string.saved_task_horserace),kytkimet[9].isChecked());
        editor.putBoolean(getString(R.string.saved_task_busdriver),kytkimet[10].isChecked());
        editor.putBoolean(getString(R.string.saved_task_stack),kytkimet[11].isChecked());
        editor.apply();
    }
}