package com.example.alne.view.Home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.alne.databinding.FragmentHomeBinding
import com.example.alne.domain.model.recipe
import com.example.alne.view.Recipe.RecipeDetailActivity
import com.example.alne.viewmodel.HomeViewModel
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    var items: ArrayList<recipe> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        setBannerAds()

        val adapter = HomeRecipeRankRVAdapter(requireContext())
        adapter.setMyItemClickListener(object: HomeRecipeRankRVAdapter.setOnClickListener{
            override fun clickItem(recipe: recipe) {
                var intent = Intent(requireContext(), RecipeDetailActivity::class.java)
                intent.putExtra("recipe", Gson().toJson(recipe))
                startActivity(intent)
            }
        })
        binding.homeRecipeRankRv.adapter = adapter
        binding.homeItemRv.adapter = adapter

        viewModel.getRecipeLiveData.observe(viewLifecycleOwner, Observer {data ->
            Log.d("viewModel" , data.toString())
            items.addAll(data)
            adapter.addItems(items)
        })

        return binding.root
    }

    private fun setBannerAds(){
        MobileAds.initialize(requireContext())
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
        binding.adView.adListener = object: AdListener() {
            override fun onAdClicked() {
                Log.d("adView", "배너 광고를 클릭했습니다")
            }

            override fun onAdClosed() {
                Log.d("adView", "배너 광고를 닫았습니다")
            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                Log.d("adView", "배너 광고가 로드 실패했습니다")
            }

            override fun onAdLoaded() {
                Log.d("adView", "배너 광고가 로드되었습니다")
            }

            override fun onAdOpened() {
                Log.d("adView", "배너 광고를 열었습니다")
            }
        }
    }


}