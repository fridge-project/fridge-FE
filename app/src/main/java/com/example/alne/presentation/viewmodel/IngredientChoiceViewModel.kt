package com.example.alne.viewmodelprivate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alne.data.model.RoomIngredient
import com.example.alne.domain.repository.fridgeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IngredientChoiceViewModel @Inject constructor(
    private val repository: fridgeRepository
): ViewModel() {

    //재료 초기화
    private val _getFridgeLiveData = MutableLiveData<ArrayList<RoomIngredient>>()
    val getFridgeLiveData: LiveData<ArrayList<RoomIngredient>> = _getFridgeLiveData

    init {
        viewModelScope.launch(Dispatchers.IO){
            _getFridgeLiveData.postValue(repository.getAllIngredient())
        }
    }



}