package com.example.alne.view.Fridge

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alne.databinding.IngredientchoiceBinding
import com.example.alne.viewmodelprivate.IngredientChoiceViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class IngredientChoice: BottomSheetDialogFragment() {

    lateinit var binding: IngredientchoiceBinding
    lateinit var viewModel: IngredientChoiceViewModel
    private var listener: OnSendFromBottomSheetDialog? = null
    var adapter: IngredientChoiceAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = IngredientchoiceBinding.inflate(layoutInflater)
        binding.ingredientChoiceSv.queryHint = Html.fromHtml("<font color = #000000>" + "재료명을 입력해 검색해보세요." + "</font>")


        viewModel = ViewModelProvider(this).get(IngredientChoiceViewModel::class.java)

        viewModel.getFridgeLiveData.observe(viewLifecycleOwner, Observer { items ->
            adapter = IngredientChoiceAdapter(items)
            adapter!!.setMyItemClickListener(object: IngredientChoiceAdapter.MyItemClickListener{
                override fun onItemClick(name: String) {
                    listener?.sendValue(name)
                    dismiss()
                }
            })
            binding.ingredientChoiceRv.adapter = adapter
            binding.ingredientChoiceRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        })

        binding.ingredientChoiceSv.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter?.filter?.filter(newText)
                return false
            }

        })

        return binding.root
    }

    interface OnSendFromBottomSheetDialog {
        fun sendValue(value: String)
    }

    fun setCallback(listener: OnSendFromBottomSheetDialog) {
        this.listener = listener
    }

}