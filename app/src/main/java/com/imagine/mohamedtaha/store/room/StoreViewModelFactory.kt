package com.imagine.mohamedtaha.store.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class StoreViewModelFactory(private val repository: StoreRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StoreViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StoreViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}