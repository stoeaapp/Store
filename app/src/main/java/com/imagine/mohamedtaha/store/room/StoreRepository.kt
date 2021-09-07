package com.imagine.mohamedtaha.store.room

import androidx.annotation.WorkerThread
import com.imagine.mohamedtaha.store.room.data.*
import kotlinx.coroutines.flow.Flow

class StoreRepository(private val storeDao: StoreDao) {
    //_____________________________Methods Categories____________________________

    val getAllCategories :Flow<List<Categories>> = storeDao.getAllCategories()
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertCategory(categories: Categories){
        storeDao.insertCategory(categories)
    }
    //_____________________________Methods Permissions____________________________

    val getAllPermissions :Flow<List<Permissions>> = storeDao.getAllPermisisons()
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertPermissions(permissions: Permissions){
        storeDao.insertPermissions(permissions)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updatePermissions(id:Long,permission_name:String,notes:String,update_date:String){
        storeDao.updatePermissions(id,permission_name,notes,update_date)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deletePermission(id:Long){
        storeDao.deletePermission(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updatePermissions(permissions: Permissions){
        storeDao.updatePermissions(permissions)
    }

    //_____________________________Methods Stores____________________________

    val getAllStores :Flow<List<Stores>> = storeDao.getAllStores()
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertStores(stores: Stores){
        storeDao.insertStore(stores)
    }



    val getAllStokeHouseByCategoryAndStory: kotlinx.coroutines.flow.Flow<List<ItemStore>> = storeDao.getAllStokeHouseByCategoryAndStory()
    val getAllStokeWareHouse: kotlinx.coroutines.flow.Flow<List<StockingHouse>> = storeDao.getAllStokeWareHouse()
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertStokeWarehouse(stockingHouse: StockingHouse){
        storeDao.insertStokeWarehouse(stockingHouse)
    }

}