package com.imagine.mohamedtaha.store.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.imagine.mohamedtaha.store.room.data.*
import kotlinx.coroutines.launch

class StoreViewModel(private val repository: StoreRepository) :ViewModel(){

    //_____________________________Methods Categories____________________________

    val allCategoriesLiveData:LiveData<List<Categories>> = repository.getAllCategories.asLiveData()
    fun insertCategory(categories: Categories) = viewModelScope.launch { repository.insertCategory(categories) }

    fun updateCategory(id:Long,category_category:String,natural_category:String,notes:String,update_date:String) = viewModelScope.launch {
        repository.updateCategory(id,category_category,natural_category,notes,update_date) }

    fun deleteCategory(id: Long) = viewModelScope.launch { repository.deleteCategory(id) }

    //_____________________________Methods Permissions____________________________

    val allPermissionsLiveData:LiveData<List<Permissions>> = repository.getAllPermissions.asLiveData()
    fun insertPermissions(permissions: Permissions) = viewModelScope.launch { repository.insertPermissions(permissions) }
    fun updatePermissions(permissions: Permissions) = viewModelScope.launch { repository.updatePermissions(permissions) }
    fun updatePermissions(id:Long,permission_name:String,notes:String,update_date:String) = viewModelScope.launch {
        repository.updatePermissions(id,permission_name,notes,update_date) }
    fun deletePermissions(id: Long) = viewModelScope.launch { repository.deletePermission(id) }


    //_____________________________Methods Stores____________________________
    val allStoresLiveData:LiveData<List<Stores>> =repository.getAllStores.asLiveData()
    fun insertStore(stores: Stores) = viewModelScope.launch { repository.insertStores(stores) }
    fun updateStore(id:Long,type_store:String,notes:String,update_date: String) = viewModelScope.launch {
        repository.updateStore(id,type_store,notes,update_date) }
    fun deleteStore(id:Long) = viewModelScope.launch { repository.deleteStore(id) }

    //_____________________________Methods Convert Stores____________________________
    fun insertConvertStore(convertStores: ConvertStores) = viewModelScope.launch { repository.insertConvertStore(convertStores) }
    fun updateConvertStore(id:Long,type_store:String,notes:String,update_date: String) = viewModelScope.launch {
        repository.updateConvertStore(id,type_store,notes,update_date) }
    fun deleteConvertStore(id:Long) = viewModelScope.launch { repository.deleteConvertStore(id) }


    val AllStokeHouseByCategoryAndStory:LiveData<List<ItemStore>> = repository.getAllStokeHouseByCategoryAndStory.asLiveData()
    val AllStokeWareHouse:LiveData<List<StockingHouse>> = repository.getAllStokeWareHouse.asLiveData()
    fun  insertStokeWarehouse(stockingHouse: StockingHouse) = viewModelScope.launch {
        repository.insertStokeWarehouse(stockingHouse)
    }
}