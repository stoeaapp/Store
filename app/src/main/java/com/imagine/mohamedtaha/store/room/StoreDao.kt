package com.imagine.mohamedtaha.store.room

import androidx.room.*
import com.imagine.mohamedtaha.store.room.data.*

@Dao
interface StoreDao {
    //_____________________________Methods Permissions____________________________

    //___________________________________Add Permissions__________________________
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPermissions(permissions: Permissions)

    //___________________________________Update Permissions__________________________
    @Query("UPDATE permissions SET  permission_name =:permission_name ,notes =:notes ,updated_at =:update_date WHERE id = :id")
    suspend fun updatePermissions(id:Long,permission_name:String,notes:String,update_date:String)
    @Update()
    suspend fun updatePermissions(permissions: Permissions)

    //___________________________________Delete Permissions_______________________
    @Query("DELETE FROM permissions WHERE id= :id")
    suspend fun deletePermission(id:Long)

    //____________________getting all Permissions_________________________________
    @Query("SELECT * FROM permissions")
    fun getAllPermisisons(): kotlinx.coroutines.flow.Flow<List<Permissions>>


    //_____________________________Methods Categories____________________________
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(categories: Categories)

    //____________________getting all Categories_________________________________
    @Query("SELECT * FROM categories")
    fun getAllCategories(): kotlinx.coroutines.flow.Flow<List<Categories>>

    //_____________________________Methods Stores____________________________
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStore(stores: Stores)

    //____________________getting all Categories_________________________________
    @Query("SELECT * FROM type_store")
    fun getAllStores(): kotlinx.coroutines.flow.Flow<List<Stores>>


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