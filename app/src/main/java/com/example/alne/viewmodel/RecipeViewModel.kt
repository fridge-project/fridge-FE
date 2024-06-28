package com.example.alne.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alne.repository.recipeRepository
import com.example.alne.room.model.recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeViewModel() : ViewModel() {

    private val repository = recipeRepository()
    private val _getRecipeLiveData = MutableLiveData<ArrayList<recipe>>()
    val getRecipeLiveData: LiveData<ArrayList<recipe>> = _getRecipeLiveData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _getRecipeLiveData.postValue(repository.getAllRecipe())
        }
    }
}