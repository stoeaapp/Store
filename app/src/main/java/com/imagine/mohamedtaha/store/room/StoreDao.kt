package com.imagine.mohamedtaha.store.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.imagine.mohamedtaha.store.room.data.ItemStore
import com.imagine.mohamedtaha.store.room.data.StockingHouse
import com.imagine.mohamedtaha.store.ui.activity.StockingWarehouse

@Dao
interface StoreDao {
    //_____________________________Methods StokeWareHouse____________________________
    //___________________________________Add StokeWareHouse____________________________
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStokeWarehouse(stockingHouse: StockingHouse)

    //____________________getting all StokeWareHouse under Store and Category____________________________
    @Query("SELECT * FROM stores")
    fun getAllStokeHouseByCategoryAndStory(): kotlinx.coroutines.flow.Flow<List<ItemStore>>
    //____________________getting all StokeWareHouse under Store and Category____________________________
    @Query("SELECT * FROM stocking_ware_house")
    fun getAllStokeWareHouse(): kotlinx.coroutines.flow.Flow<List<StockingHouse>>

}