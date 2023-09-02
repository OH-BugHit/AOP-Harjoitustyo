package com.oh.ready4play;

import android.graphics.Color;    //MAINOSRIVI
import android.graphics.drawable.ColorDrawable;    //MAINOSRIVI
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;    //MAINOSRIVI

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;    //MAINOSRIVI
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.LoadAdError;    //MAINOSRIVI
import com.google.android.gms.ads.MobileAds;    //MAINOSRIVI
import com.google.android.gms.ads.initialization.InitializationStatus;    //MAINOSRIVI
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;    //MAINOSRIVI
import com.google.android.gms.ads.nativead.NativeAd;    //MAINOSRIVI
import com.google.android.gms.ads.nativead.NativeAdOptions;    //MAINOSRIVI
import com.oh.ready4play.nativetemplate.NativeTemplateStyle;    //MAINOSRIVI
import com.oh.ready4play.nativetemplate.TemplateView;    //MAINOSRIVI

/**
 * MainActivity. Sovellus käynnistyy täältä
 * @version 1.0
 * @author Olli Hilke
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Mainosten lataaja
     */
    //public AdLoader adLoader;    //MAINOSRIVI
    /**
     * POISTA KOMMENTOINTI JA LISÄÄ ADMOB TUNNUKSESI
     * Sisältää mainostajan tunnuksen.
     * Tässä harjoitustyössä käytetty testitunnusta
     */
    //private static final String ADMOB_AD_UNIT_ID = "ca-app-pub-3940256099942544/2247696110";    //MAINOSRIVI
    /**
     * Tämä, luotu, käytettävä MainActivity-olio.
     */
    public static MainActivity INSTANCE;
    /**
     * Muuttuja sisältää tiedon onko mainoksenlataaja valmis suljettavaksi.
     */
    //public static boolean mainosvalmis = false;    //MAINOSRIVI

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow(). addFlags (WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        MainActivity.INSTANCE = this;
        setContentView(R.layout.activity_main);

        /*
        //Mainokset
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        //Lataa mainos
        adLoader = new AdLoader.Builder(MainActivity.this, ADMOB_AD_UNIT_ID)
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        if (adLoader.isLoading()) {
                            Toast.makeText(MainActivity.this,"Native ads loaded successfully", Toast.LENGTH_SHORT).show();
                        }
                        //Tuhoaa mainoksen jos sovellus on "tuhottu"
                        if (isDestroyed()) {
                            nativeAd.destroy();
                        }

                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().withMainBackgroundColor(new ColorDrawable(Color.WHITE)).build(); //Mainoksen tausta(väri)

                        //mediumTemplate

                        TemplateView template = findViewById(R.id.medium_mainos_template);
                        template.setStyles(styles);
                        template.setNativeAd(nativeAd);
                        mainosvalmis = true;
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        Toast.makeText(MainActivity.this,"Failed to load native ads",Toast.LENGTH_SHORT).show();
                        mainosvalmis = true;
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        // Ei tarvetta tässä
                        .build())
                .build();

                //Mainokset päättyy
         */
    }
}