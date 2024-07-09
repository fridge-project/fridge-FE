package com.example.alne.view.Fridge

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alne.GlobalApplication
import com.example.alne.databinding.FragmentFridgeAllBinding
import com.example.alne.data.model.FridgeIngredient
import com.example.alne.utils.RESPONSE_STATUS
import com.example.alne.viewmodel.FridgeViewModel


class FridgeAllFragment : Fragment(), MyCustomDialogDetailInterface {
    lateinit var binding: FragmentFridgeAllBinding
    lateinit var viewModel: FridgeViewModel
    lateinit var fridgeadapter: FridgeAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentFridgeAllBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(FridgeViewModel::class.java)
        Log.d("FridgeAllFragment", "onCreateView")

        //RecyclerView 초기화
        fridgeRecyclerViewSettings()
        //현재 재료 목록 옵저버 패턴
        viewModel.getFridgeLiveData.observe(viewLifecycleOwner, Observer { items ->
            Log.d("getFridge", items.toString())
            fridgeadapter.addAllFood(items)
        })

        return binding.root
    }

    private fun fridgeRecyclerViewSettings(){
        fridgeadapter = FridgeAdapter(requireContext())
        binding.fridgeAllRv.adapter = fridgeadapter
        binding.fridgeAllRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false )
        fridgeadapter.setMyItemClickListener(object: FridgeAdapter.MyItemClickListener {
            // 보유재료 클릭 시
            override fun onItemClick(food: FridgeIngredient) {
                CustomDialogDetail(requireContext(),food, this@FridgeAllFragment).show(requireActivity().supportFragmentManager, "CustomDialog")
            }

            // 보유재료 삭제 기능
            override fun onInfoClick(view: View, position: Int) {
//                    viewModel.deleteFridgeFood(UserId(
//                        getUserToken()?.userId!!,
//                        fridgeadapter.items[position].userId!!
//                    ))
            }
        })
    }

    // 재료 수정하기(편집)
//    override fun onSubmitBtnDetailClicked(food: FridgeIngredient, photoFile: File?) {
//        Log.d("onSubmitBtnDetailClicked", "launch")
//
//        //viewModel.addFridgeData(getUserToken()?.accessToken!!, food, photoFile)
//    }

    // 재료 저장하기 버튼 클릭 시
    override fun onSubmitBtnDetailClicked(food: FridgeIngredient) {
        Log.d("onSubmitBtnDetailClicked", "launch")
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

}