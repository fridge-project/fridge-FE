package com.example.alne.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alne.domain.model.recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

): ViewModel() {

    private val _getRecipeLiveData = MutableLiveData<ArrayList<recipe>>()
    val getRecipeLiveData: LiveData<ArrayList<recipe>> = _getRecipeLiveData

    private val _getFridgeLiveData = MutableLiveData<ArrayList<recipe>>()
    val getFridgeLiveData: LiveData<ArrayList<recipe>> = _getFridgeLiveData

}