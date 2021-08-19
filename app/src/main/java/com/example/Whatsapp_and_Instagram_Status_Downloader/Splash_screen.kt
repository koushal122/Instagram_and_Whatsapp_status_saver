package com.example.Whatsapp_and_Instagram_Status_Downloader

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.Whatsapp_and_Instagram_Status_Saver.Instagram_link_page
import com.example.Whatsapp_and_Instagram_Status_Saver.R
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import java.util.*
import com.example.Whatsapp_and_Instagram_Status_Downloader.MainActivity

@Suppress("DEPRECATION")
class Splash_screen : AppCompatActivity() {

    private var mInterstitialAd: InterstitialAd? = null
    private final var TAG = "MainActivity"
    lateinit var mAdView : AdView
    lateinit var mAdViewupper : AdView

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        var banner: AdView=findViewById(R.id.adView)
        var textView:TextView=findViewById(R.id.textView);
        val cardview=findViewById<CardView>(R.id.cardView2)
        Objects.requireNonNull(supportActionBar)!!.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val whatsapp=findViewById<CardView>(R.id.Whatsapp_cardView)
        val instagram=findViewById<CardView>(R.id.Instagram_cardview)

        var adRequest = AdRequest.Builder().build()
        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError?.message)
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {

                mInterstitialAd = interstitialAd
            }
        })
        whatsapp.setOnClickListener{
            val intent = Intent(this@Splash_screen,MainActivity::class.java)
            startActivity(intent)
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(this)
            } else {
            }
            finish()
        }
        instagram.setOnClickListener{
            val fragment2 = Instagram_link_page();
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(this)
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.")
            }
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.Splash_screen_constrint_layout, fragment2)
                    .commit()
             textView.visibility=View.GONE
             whatsapp.visibility=View.GONE;
             instagram.visibility=View.GONE
             cardview.visibility=View.GONE
             banner.visibility=View.GONE

        }
        MobileAds.initialize(this) { }
        mAdView = findViewById(R.id.adView)
        mAdView.loadAd(adRequest)
        mAdViewupper = findViewById(R.id.adView2)
        mAdViewupper.loadAd(adRequest)

        mAdView.adListener = object: AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                super.onAdLoaded()
            }

            override fun onAdFailedToLoad(adError : LoadAdError) {
                // Code to be executed when an ad request fails.
                super.onAdFailedToLoad(adError)
                mAdView.loadAd(adRequest)
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                super.onAdOpened();
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                super.onAdClicked()
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                super.onAdClosed()
            }
        }


        mAdViewupper.adListener = object: AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                super.onAdLoaded()

            }

            override fun onAdFailedToLoad(adError : LoadAdError) {
                // Code to be executed when an ad request fails.
                super.onAdFailedToLoad(adError)
                mAdViewupper.loadAd(adRequest)
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                super.onAdOpened();
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                super.onAdClicked()
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                super.onAdClosed()
            }
        }


    }


}