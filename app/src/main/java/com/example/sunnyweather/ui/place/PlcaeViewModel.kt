package com.example.sunnyweather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.sunnyweather.logic.Reponsitory
import com.example.sunnyweather.logic.model.Place

class PlcaeViewModel :ViewModel() {

    private val searchLiveData = MutableLiveData<String>()
    val listPlace = ArrayList<Place>()
    val placeLiveData = Transformations.switchMap(searchLiveData){
        query -> Reponsitory.searchPlaces(query)
    }
    fun searchPlaces(query : String){
        searchLiveData.value = query
    }
}