package com.oh.ready4play;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.oh.ready4play.nativetemplate.NativeTemplateStyle;
import com.oh.ready4play.nativetemplate.TemplateView;

public class MainActivity extends AppCompatActivity {
    public AdLoader adLoader;
    public static final String ADMOB_AD_UNIT_ID = "ca-app-pub-3940256099942544/2247696110";
    public static MainActivity INSTANCE;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.INSTANCE = this;
        setContentView(R.layout.activity_main);


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
                        if (isDestroyed()) {
                            nativeAd.destroy();
                        }

                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().withMainBackgroundColor(new ColorDrawable(Color.WHITE)).build(); //Mainoksen tausta (väri)

                        //mediumTemplate
                        TemplateView template = findViewById(R.id.my_template);
                        template.setStyles(styles);
                        template.setNativeAd(nativeAd);
                    }
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        Toast.makeText(MainActivity.this,"Failed to load native ads",Toast.LENGTH_SHORT).show();
                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build())
                .build();
    }
}