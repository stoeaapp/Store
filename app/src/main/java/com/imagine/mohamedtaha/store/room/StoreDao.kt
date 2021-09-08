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
    fun getAllPermission(): kotlinx.coroutines.flow.Flow<List<Permissions>>


    //_____________________________Methods Categories____________________________
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(categories: Categories)

    //____________________getting all Categories_________________________________
    @Query("SELECT * FROM categories")
    fun getAllCategories(): kotlinx.coroutines.flow.Flow<List<Categories>>
    //___________________________________Update Categories__________________________
    @Query("UPDATE categories  SET category_name =:category_name ,natural_category = :natural_category,notes =:notes ,updated_at =:update_date WHERE id = :id")
    suspend fun updateCategory(id:Long,category_name:String,natural_category:String,notes:String,update_date:String)

    //___________________________________Delete Store_______________________________
    @Query("DELETE FROM categories WHERE id = :id")
    suspend fun deleteCategory(id:Long)
    //_____________________________Methods Stores____________________________
    //___________________________________Add Stores__________________________

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStore(stores: Stores)

    //___________________________________Update Stores__________________________
    @Query("UPDATE type_store  SET  type_store =:type_store ,notes =:notes ,updated_at =:update_date WHERE id = :id")
    suspend fun updateStore(id:Long,type_store:String,notes:String,update_date:String)

    //____________________getting all Stores_________________________________
    @Query("SELECT * FROM type_store")
    fun getAllStores(): kotlinx.coroutines.flow.Flow<List<Stores>>

    //___________________________________Delete Store_______________________________
    @Query("DELETE FROM type_store WHERE id = :id")
    suspend fun deleteStore(id:Long)

    //_____________________________Methods Convert Store_____________________________

    //___________________________________Add Convert Store __________________________
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConvertStore(convertStores: ConvertStores)

    //___________________________________Update Convert Store__________________________
    @Query("UPDATE convert_store SET  convert_store =:convert_store ,notes =:notes ,updated_at =:update_date WHERE id = :id")
    suspend fun updateConvertStore(id:Long,convert_store:String,notes:String,update_date:String)

    @Update()
    suspend fun updateConvertStore(convertStores: ConvertStores)

    //___________________________________Delete Convert Store_______________________
    @Query("DELETE FROM convert_store WHERE id= :id")
    suspend fun deleteConvertStore(id:Long)

    //____________________getting all Convert Store_________________________________
    @Query("SELECT * FROM convert_store")
    fun getAllConvertStore(): kotlinx.coroutines.flow.Flow<List<ConvertStores>>

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