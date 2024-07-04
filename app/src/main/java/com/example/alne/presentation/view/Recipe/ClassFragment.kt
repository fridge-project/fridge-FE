package com.example.alne.view.Recipe

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.alne.databinding.FragmentClassBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ClassFragment : BottomSheetDialogFragment(){
    lateinit var binding: FragmentClassBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentClassBinding.inflate(layoutInflater)
        setOnClickListener()
        return binding.root
    }
    private fun setOnClickListener(){
        binding.classKoreanFoodBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classJapanFoodBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classChinaFoodBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classItalyFoodBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classAsianfoodBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classFishBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classStirFryBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classPizzaBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classSteamedBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classHotpotBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classSaladBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classNoodleBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classRiceCakeBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classSeasoningSauceBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classRiceBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classSoupBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classPancakeBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classBreadBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classRisottoBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classFriedFoodBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classLunchBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classKimchiBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classItalianBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classAfforestationBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classBeverageBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classBurgerBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classGrains1Bt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classVegeBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classMushroomBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classSeaweedBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classNutsBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classBeefBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classPigbeefBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classChickenBeefBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classProcessedFoodsBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classFlourBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classEggBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classFish1Bt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classBeanBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classEtcBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classVegetablesBt.setOnClickListener { it.isSelected = !it.isSelected }
        binding.classResetBt.setOnClickListener {
            binding.classKoreanFoodBt.isSelected = false
            binding.classJapanFoodBt.isSelected = false
            binding.classChinaFoodBt.isSelected = false
            binding.classItalyFoodBt.isSelected = false
            binding.classAsianfoodBt.isSelected = false
            binding.classFishBt.isSelected = false
            binding.classStirFryBt.isSelected = false
            binding.classPizzaBt.isSelected = false
            binding.classSteamedBt.isSelected = false
            binding.classHotpotBt.isSelected = false
            binding.classSaladBt.isSelected = false
            binding.classNoodleBt.isSelected = false
            binding.classRiceCakeBt.isSelected = false
            binding.classSeasoningSauceBt.isSelected = false
            binding.classRiceBt.isSelected = false
            binding.classSoupBt.isSelected = false
            binding.classPancakeBt.isSelected = false
            binding.classBreadBt.isSelected = false
            binding.classRisottoBt.isSelected = false
            binding.classFriedFoodBt.isSelected = false
            binding.classLunchBt.isSelected = false
            binding.classKimchiBt.isSelected = false
            binding.classItalianBt.isSelected = false
            binding.classAfforestationBt.isSelected = false
            binding.classBeverageBt.isSelected = false
            binding.classBurgerBt.isSelected = false
            binding.classGrains1Bt.isSelected = false
            binding.classVegeBt.isSelected = false
            binding.classMushroomBt.isSelected = false
            binding.classSeaweedBt.isSelected = false
            binding.classNutsBt.isSelected = false
            binding.classBeefBt.isSelected = false
            binding.classPigbeefBt.isSelected = false
            binding.classChickenBeefBt.isSelected = false
            binding.classProcessedFoodsBt.isSelected = false
            binding.classFlourBt.isSelected = false
            binding.classEggBt.isSelected = false
            binding.classFish1Bt.isSelected = false
            binding.classBeanBt.isSelected = false
            binding.classEtcBt.isSelected = false
            binding.classVegetablesBt.isSelected = false
        }

        binding.classSetBt.setOnClickListener {
            setClass()
            dismiss()
        }
    }
    private fun setClass(){

    }
}