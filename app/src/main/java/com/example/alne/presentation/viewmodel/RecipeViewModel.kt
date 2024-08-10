package com.example.alne.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.alne.domain.repository.recipeRepository
import com.example.alne.domain.model.recipe
import com.example.alne.utils.Recipe_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repository: recipeRepository
) : ViewModel() {

    fun getRecipePagingList(category : String?, type: Recipe_TYPE) : Flow<PagingData<recipe>> {
        var data = repository.getRecipePagingSource(category, type).cachedIn(viewModelScope)
        return data
    }
}