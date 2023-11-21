package com.byagowi.persiancalendar.ui.Ediori

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.adivery.sdk.Adivery
import com.adivery.sdk.AdiveryAdListener
import com.adivery.sdk.AdiveryBannerAdView
import com.byagowi.persiancalendar.databinding.FragmentBlankBinding


class BlankFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentBlankBinding.inflate(LayoutInflater.from(activity), container, false)


      Advertising(binding)





        return binding.root

    }
    private fun Advertising(binding: FragmentBlankBinding) {
        val appId = "e572dc77-9ea6-4327-9b0b-26e2601b1370"
        Adivery.configure(requireActivity().application, appId) // Use requireActivity().application instead of getApplication()
        val bannerAd: AdiveryBannerAdView = binding.bannerAd1

        bannerAd.setBannerAdListener(object : AdiveryAdListener() {
            override fun onAdLoaded() {
                Log.d("Advertising", "Ad loaded")

            }

            override fun onError(reason: String) {


                Log.d("Advertising", reason)
            }

            override fun onAdClicked() {
                // User clicked on the ad
            }
        })

        bannerAd.loadAd()
    }
}

