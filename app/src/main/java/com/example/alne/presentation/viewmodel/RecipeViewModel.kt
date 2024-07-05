package com.example.alne.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.alne.repository.recipeRepository
import com.example.alne.room.model.recipe
import com.example.alne.utils.Recipe_TYPE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RecipeViewModel() : ViewModel() {

    private val repository = recipeRepository()
    private val _getRecipeLiveData = MutableLiveData<ArrayList<recipe>>()
    val getRecipeLiveData: LiveData<ArrayList<recipe>> = _getRecipeLiveData


    init {
        viewModelScope.launch(Dispatchers.IO) {
           // _getRecipeLiveData.postValue(repository.getAllRecipe())
        }
    }

    fun getRecipePagingList(category : String?, type: Recipe_TYPE) : Flow<PagingData<recipe>> {
        var data = repository.getRecipePagingSource(category, type).cachedIn(viewModelScope)
        return data
    }

//    fun getRecipePagingList(vararg : String? , type: Recipe_TYPE) : Flow<PagingData<recipe>> {
//        var data = repository.getRecipePagingSource(vararg, type).cachedIn(viewModelScope)
//        return data
//    }
}