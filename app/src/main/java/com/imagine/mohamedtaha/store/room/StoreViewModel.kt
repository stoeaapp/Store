package com.imagine.mohamedtaha.store.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.imagine.mohamedtaha.store.room.data.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class StoreViewModel @Inject constructor(private val repository: StoreRepository) : ViewModel() {

    //_____________________________Methods Categories____________________________
    val allCategoriesLiveData: LiveData<List<Categories>> = repository.getAllCategories.asLiveData()
    val allNameCategoriesLiveData: LiveData<List<String>> = map(allCategoriesLiveData) { it.map { it.categoryName } }
    fun insertCategory(categories: Categories) = viewModelScope.launch {
        try {
            repository.insertCategory(categories)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun updateCategory(id: Long, category_category: String, natural_category: String, notes: String, update_date: String) = viewModelScope.launch {
        repository.updateCategory(id, category_category, natural_category, notes, update_date)
    }

    fun deleteCategory(id: Long) = viewModelScope.launch { repository.deleteCategory(id) }

    //_____________________________Methods Permissions____________________________

    val allPermissionsLiveData: LiveData<List<Permissions>> = repository.getAllPermissions.asLiveData()
    val allNamePermissionsLiveData: LiveData<List<String>> = map(allPermissionsLiveData) {
        it.map {
            it.permissionName
        }
    }

    fun insertPermissions(permissions: Permissions) = viewModelScope.launch { repository.insertPermissions(permissions) }
//    fun insertPermissionsLong(permissions: Permissions):Long{
//
//
//        return viewModelScope.launch { repository.insertPermissions(permissions) }
//    }

    fun updatePermissions(permissions: Permissions) = viewModelScope.launch { repository.updatePermissions(permissions) }
    fun updatePermissions(id: Long, permission_name: String, notes: String, update_date: String) = viewModelScope.launch {
        repository.updatePermissions(id, permission_name, notes, update_date)
    }

    fun deletePermissions(id: Long) = viewModelScope.launch { repository.deletePermission(id) }


    //_____________________________Methods Stores____________________________
    val allStoresLiveData: LiveData<List<Stores>> = repository.getAllStores.asLiveData()
    val allNameStoresLiveData: LiveData<List<String>> = map(allStoresLiveData) { it.map { it.typeStore } }
    fun insertStore(stores: Stores) = viewModelScope.launch { repository.insertStores(stores) }
    fun updateStore(id: Long, type_store: String, notes: String, update_date: String) = viewModelScope.launch {
        repository.updateStore(id, type_store, notes, update_date)
    }

    fun deleteStore(id: Long) = viewModelScope.launch { repository.deleteStore(id) }

    //_____________________________Methods Convert Stores____________________________
    fun insertConvertStore(convertStores: ConvertStores) = viewModelScope.launch { repository.insertConvertStore(convertStores) }
    fun updateConvertStore(id: Long, type_store: String, notes: String, update_date: String) = viewModelScope.launch {
        repository.updateConvertStore(id, type_store, notes, update_date)
    }

    fun deleteConvertStore(id: Long) = viewModelScope.launch { repository.deleteConvertStore(id) }

    val AllStokeWareHouse: LiveData<List<StockingHouse>> = repository.getAllStokeWareHouse.asLiveData()
    val allStokeWareHouseWitCategoriesAndStoresShow: LiveData<List<ShowStockWare>> = repository.getAllStokeWareHouseWitCategoriesAndStoresShow.asLiveData()
    val allDailyMovement: LiveData<List<ShowDailyMovements>> = repository.getAllDailyMovement.asLiveData()

    fun insertStokeWarehouse(stockingHouse: StockingHouse) = viewModelScope.launch {
        repository.insertStokeWarehouse(stockingHouse)
    }

    fun updateStockWarehouse(id: Long, store_id: Long, first_balance: String, notes: String) = viewModelScope.launch {
        repository.updateStockWarehouse(id, store_id, first_balance, notes)
    }

    fun deleteStokeWarehouse(id: Long) = viewModelScope.launch { repository.deleteStockWarehouse(id) }

    //__________________________________Method Daily Movement
    fun insertDailyMovement(movements: DailyMovements) = viewModelScope.launch { repository.insertDailyMovement(movements) }

    fun updateDailyMovement(id: Long, permission_id: Long, category_id: Long, store_id: Long, convert_to: Long, incoming: Int, issued: Int, update_date: String) = viewModelScope.launch {
        repository.updateDailyMovement(id, permission_id, category_id, store_id, convert_to, incoming, issued, update_date)
    }

    fun deleteDailyMovement(id: Long) = viewModelScope.launch { repository.deleteDailyMovement(id) }
    fun getFirstBalanceString(category_id: Long, store_id: Long) = repository.getFirstBalanceString(category_id, store_id).asLiveData()
    fun getIncomingString(category_id: Long, store_id: Long) = repository.getIncomingString(category_id, store_id).asLiveData()
    fun getIssuedString(category_id: Long, store_id: Long) = repository.getIssuedString(category_id, store_id).asLiveData()
    fun getIssuedConvertToString(category_id: Long, convert_to: Long) = repository.getIssuedConvertToString(category_id, convert_to).asLiveData()
//    fun isExistsInStockWarehouse(id: Long) = viewModelScope.launch {
//        try{
//            repository.isExistsInStockWarehouse(id)
//        }catch (e:java.lang.Exception){
//            e.printStackTrace()
//        }
//      }

    suspend fun isExistsInStockWarehouse(id: Long) = repository.isExistsInStockWarehouse(id)


    fun isExistsInDailyMovements(id: Long) = viewModelScope.launch { repository.isExistsInDailyMovements(id) }


}