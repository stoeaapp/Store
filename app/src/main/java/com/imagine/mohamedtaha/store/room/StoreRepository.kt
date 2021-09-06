package com.imagine.mohamedtaha.store.room

import androidx.annotation.WorkerThread
import com.imagine.mohamedtaha.store.room.data.ItemStore
import com.imagine.mohamedtaha.store.room.data.StockingHouse

class StoreRepository(private val storeDao: StoreDao) {
    val getAllStokeHouseByCategoryAndStory: kotlinx.coroutines.flow.Flow<List<ItemStore>> = storeDao.getAllStokeHouseByCategoryAndStory()
    val getAllStokeWareHouse: kotlinx.coroutines.flow.Flow<List<StockingHouse>> = storeDao.getAllStokeWareHouse()
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertStokeWarehouse(stockingHouse: StockingHouse){
        storeDao.insertStokeWarehouse(stockingHouse)
    }

}