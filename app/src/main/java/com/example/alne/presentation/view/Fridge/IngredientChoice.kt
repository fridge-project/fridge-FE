package com.example.alne.view.Fridge

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alne.databinding.IngredientchoiceBinding
import com.example.alne.viewmodelprivate.IngredientChoiceViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IngredientChoice: BottomSheetDialogFragment() {

    lateinit var binding: IngredientchoiceBinding
    private val viewModel: IngredientChoiceViewModel by viewModels()
    private var listener: OnSendFromBottomSheetDialog? = null
    lateinit var adapter: IngredientChoiceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = IngredientchoiceBinding.inflate(layoutInflater)
        binding.ingredientChoiceSv.queryHint = Html.fromHtml("<font color = #000000>" + "재료명을 입력해 검색해보세요." + "</font>")

        ingredientRecyclerViewSettings()
        viewModel.getFridgeLiveData.observe(viewLifecycleOwner, Observer { items ->
            Log.d("ObserverItems", items.toString())
            adapter.addAllItems(items)
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

    private fun ingredientRecyclerViewSettings(){
        adapter = IngredientChoiceAdapter()
        adapter.setMyItemClickListener(object: IngredientChoiceAdapter.MyItemClickListener{
            override fun onItemClick(name: String) {
                listener?.sendValue(name)
                dismiss()
            }
        })
        binding.ingredientChoiceRv.adapter = adapter
        binding.ingredientChoiceRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    interface OnSendFromBottomSheetDialog {
        fun sendValue(value: String)
    }

    fun setCallback(listener: OnSendFromBottomSheetDialog) {
        this.listener = listener
    }

}