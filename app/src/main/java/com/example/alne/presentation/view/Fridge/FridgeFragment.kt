package com.example.alne.view.Fridge

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.alne.databinding.FragmentFridgeBinding
import com.example.alne.data.model.FridgeIngredient
import com.example.alne.utils.RESPONSE_STATUS
import com.example.alne.viewmodel.FridgeViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FridgeFragment : Fragment(), MyCustomDialogInterface{

    lateinit var binding: FragmentFridgeBinding
    private val information = arrayListOf("All", "냉장","냉동")
    private val viewModel : FridgeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentFridgeBinding.inflate(inflater, container, false)

        //ViewPager - TabLayout 연결
        val fridgeAdapter = FridgeVPAdapter(this)
        binding.fridgeVp.adapter = fridgeAdapter
        TabLayoutMediator(binding.fridgeTl, binding.fridgeVp){ tab, position ->
            tab.text = information[position]
        }.attach()

        // + 버튼 클릭 시
        binding.fridgeFloatingBt.setOnClickListener{
            CustomDialogAdd(requireContext(), this@FridgeFragment).show(requireActivity().supportFragmentManager, "CustomDialog")
        }

        return binding.root
    }


//    override fun onSubmitBtnClicked(food: FridgeIngredient, photoFile: File?) {
//        viewModel.addFridgeData(food, photoFile)
//    }

    // 재료 추가버튼 클릭 시
    override fun onSubmitBtnClicked(food: FridgeIngredient) {
        viewModel.addFridgeDataTest(food, completion = { responseState ->
                when (responseState) {
                    RESPONSE_STATUS.OKAY -> {
                        Log.d("onSubmitBtnDetailClicked.OKAY", "RESPONSE_STATUS.OKAY")
                    }

                    RESPONSE_STATUS.FAIL -> {
                        Log.d("onSubmitBtnDetailClicked.OKAY", "RESPONSE_STATUS.FAIL")
                    }

                    RESPONSE_STATUS.NETWORK_ERROR -> {
                        Log.d("onSubmitBtnDetailClicked.OKAY", "RESPONSE_STATUS.NETWORK_ERROR")
                    }
                }
            }
        )
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d("FridgeFragment", "destroy")
    }
}