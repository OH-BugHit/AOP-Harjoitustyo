package com.oh.ready4play.minipelit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.oh.ready4play.Kortti;
import com.oh.ready4play.MainActivity;
import com.oh.ready4play.Peli;
import com.oh.ready4play.R;

import java.util.Random;


public class Hitler extends Fragment {
    private View view;
    private ImageView ivKortti;
    private TextView tvOhjeet;
    private TextView tvArvottu;
    private final Random random = new Random();
    private int pelaajaVuorossa;
    private NativeAd nativeAd;

    public Hitler() {
        super(R.layout.fragment_hitler);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_hitler, container, false);

        pelaajaVuorossa = Peli.vuorossaPelaaja;
        tvArvottu = view.findViewById(R.id.tvArvottuPelaaja_Hitler);

        ivKortti = view.findViewById(R.id.ivKortti_Hitler);
        ivKortti.setImageResource(R.drawable.tausta);
        tvOhjeet = view.findViewById(R.id.tvOhjeistus_Hitler);

        Button btJatkaPelia = view.findViewById(R.id.btJatkaPelia_Hitler);
        btJatkaPelia.setVisibility(View.INVISIBLE);

        ivKortti.setOnClickListener(e -> {
            tvArvottu.setText("");
            Kortti kortti = arvoKortti();
            kortinTapahtuma(kortti);
            btJatkaPelia.setVisibility(View.VISIBLE);
            MainActivity.INSTANCE.adLoader.loadAd(new AdRequest.Builder().build());
        });

        btJatkaPelia.setOnClickListener(e -> {
            tvArvottu.setText("");
            tvOhjeet.setText(R.string.text_taskDescriptionHitler);
            Peli.seuraavaVuoro = true;
            Peli.fragmentManager.beginTransaction()
                    .remove(this)
                    .setReorderingAllowed(true)
                    .commit();
        });
        return view;
    }

    //TODO: TEE KUVAT KORTEISTA JA ASETA NE TÄNNE!
    private void kortinTapahtuma(Kortti kortti) {
        switch (kortti.arvo) {
            case 1 -> {
                tvOhjeet.setText(R.string.text_waterfallDesc);
            }
            case 2 -> {
                tvOhjeet.setText(R.string.text_give2);
            }
            case 3 -> {
                tvOhjeet.setText(R.string.text_drink3);
            }
            case 4 -> {
                tvOhjeet.setText(R.string.text_hitler);
            }
            case 5 -> {
                tvOhjeet.setText(R.string.text_123);
            }
            case 6 -> {
                tvOhjeet.setText(R.string.text_womenDrink);
            }
            case 7 -> {
                tvOhjeet.setText(R.string.text_category);
            }
            case 8 -> {
                tvOhjeet.setText(R.string.text_rule);
            }
            case 9 -> {
                tvOhjeet.setText(R.string.text_everybodyDrink);
            }
            case 10 -> {
                tvOhjeet.setText(R.string.text_menDrink);
            }
            case 11 -> {
                tvOhjeet.setText(R.string.text_skip);
            }
            case 12 -> {
                tvOhjeet.setText(R.string.text_hoe);
            }
            case 13 -> {
                tvOhjeet.setText(R.string.text_kingdrink);
                int arvottu = pelaajaVuorossa;
                while (arvottu == pelaajaVuorossa) {
                    arvottu = random.nextInt(Peli.pelaajamaara);
                }
                tvArvottu.setText(Peli.pelaajat.get(arvottu).pelaajanimi);
            }
        }
        ivKortti.setImageDrawable(kortti.kuva);
    }

    private Kortti arvoKortti() {
        return Peli.pakka.get(random.nextInt(Peli.pakka.size()));
    }

}
