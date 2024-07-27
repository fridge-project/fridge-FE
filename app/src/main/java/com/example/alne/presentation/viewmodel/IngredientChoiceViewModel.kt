package com.example.alne.viewmodelprivate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alne.data.model.RoomIngredient
import com.example.alne.data.model.repository.fridgeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IngredientChoiceViewModel: ViewModel() {

    private val repository = fridgeRepository()

    //재료 초기화
    private val _getFridgeLiveData = MutableLiveData<ArrayList<RoomIngredient>>()
    val getFridgeLiveData: LiveData<ArrayList<RoomIngredient>> = _getFridgeLiveData

    init {
        viewModelScope.launch(Dispatchers.IO){
            _getFridgeLiveData.postValue(repository.getAllIngredient())
        }
    }



}