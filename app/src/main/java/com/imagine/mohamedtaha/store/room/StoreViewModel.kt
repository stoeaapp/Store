package com.imagine.mohamedtaha.store.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.imagine.mohamedtaha.store.room.data.ItemStore
import com.imagine.mohamedtaha.store.room.data.StockingHouse
import kotlinx.coroutines.launch

class StoreViewModel(private val repository: StoreRepository) :ViewModel(){
    val AllStokeHouseByCategoryAndStory:LiveData<List<ItemStore>> = repository.getAllStokeHouseByCategoryAndStory.asLiveData()
    val AllStokeWareHouse:LiveData<List<StockingHouse>> = repository.getAllStokeWareHouse.asLiveData()
    fun  insertStokeWarehouse(stockingHouse: StockingHouse) = viewModelScope.launch {
        repository.insertStokeWarehouse(stockingHouse)
    }
}