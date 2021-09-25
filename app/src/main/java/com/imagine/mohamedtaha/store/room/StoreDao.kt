package com.imagine.mohamedtaha.store.room

import androidx.room.*
import com.imagine.mohamedtaha.store.room.data.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StoreDao {
    //_____________________________Methods Permissions____________________________

    //___________________________________Add Permissions__________________________
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPermissions(permissions: Permissions): Long

    //___________________________________Update Permissions__________________________
    @Query("UPDATE permissions SET  permission_name =:permission_name ,notes =:notes ,updated_at =:update_date WHERE id = :id")
    suspend fun updatePermissions(id: Long, permission_name: String, notes: String, update_date: String)

    @Update()
    suspend fun updatePermissions(permissions: Permissions): Int

    //___________________________________Delete Permissions_______________________
    @Query("DELETE FROM permissions WHERE id= :id")
    suspend fun deletePermission(id: Long)

    //____________________getting all Permissions_________________________________
    @Query("SELECT * FROM permissions ORDER BY id ASC")
    fun getAllPermission(): kotlinx.coroutines.flow.Flow<List<Permissions>>


    //_____________________________Methods Categories____________________________
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(categories: Categories)

    //____________________getting all Categories_________________________________
    @Query("SELECT * FROM categories")
    fun getAllCategories(): kotlinx.coroutines.flow.Flow<List<Categories>>

    //___________________________________Update Categories__________________________
    @Query("UPDATE categories  SET category_name =:category_name ,natural_category = :natural_category,notes =:notes ,updated_at =:update_date WHERE id = :id")
    suspend fun updateCategory(id: Long, category_name: String, natural_category: String, notes: String, update_date: String)

    //___________________________________Delete Store_______________________________
    @Query("DELETE FROM categories WHERE id = :id")
    suspend fun deleteCategory(id: Long)
    //_____________________________Methods Stores____________________________
    //___________________________________Add Stores__________________________

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStore(stores: Stores)

    //___________________________________Update Stores__________________________
    @Query("UPDATE type_store  SET  type_store =:type_store ,notes =:notes ,updated_at =:update_date WHERE id = :id")
    suspend fun updateStore(id: Long, type_store: String, notes: String, update_date: String)

    //____________________getting all Stores_________________________________
    @Query("SELECT * FROM type_store")
    fun getAllStores(): kotlinx.coroutines.flow.Flow<List<Stores>>

    //___________________________________Delete Store_______________________________
    @Query("DELETE FROM type_store WHERE id = :id")
    suspend fun deleteStore(id: Long)

    //_____________________________Methods Convert Store_____________________________

    //___________________________________Add Convert Store __________________________
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConvertStore(convertStores: ConvertStores)

    //___________________________________Update Convert Store__________________________
    @Query("UPDATE convert_store SET  convert_store =:convert_store ,notes =:notes ,updated_at =:update_date WHERE id = :id")
    suspend fun updateConvertStore(id: Long, convert_store: String, notes: String, update_date: String)

    @Update()
    suspend fun updateConvertStore(convertStores: ConvertStores)

    //___________________________________Delete Convert Store_______________________
    @Query("DELETE FROM convert_store WHERE id= :id")
    suspend fun deleteConvertStore(id: Long)

    //____________________getting all Convert Store_________________________________
    @Query("SELECT * FROM convert_store")
    fun getAllConvertStore(): kotlinx.coroutines.flow.Flow<List<ConvertStores>>

    //_____________________________Methods StokeWareHouse____________________________

    //___________________________________Add StokeWareHouse____________________________
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStokeWarehouse(stockingHouse: StockingHouse): Long

    //___________________________________Update StokeWareHouse____________________________
    @Query("UPDATE stocking_ware_house SET  category_id =:category_id,store_id =:store_id,first_balance =:first_balance ,notes =:notes")
    suspend fun updateStockWareHouse(category_id: Long, store_id: Long, first_balance: String, notes: String)

    //___________________________________Delete StokeWareHouse____________________________
    @Query("DELETE FROM stocking_ware_house WHERE id =:id")
    suspend fun deleteStockWareHouse(id: Long)

//    //____________________getting all StokeWareHouse under Store and Category____________________________
//    @Query("SELECT * FROM stores")
//    fun getAllStokeHouseByCategoryAndStory(): kotlinx.coroutines.flow.Flow<List<ItemStore>>

    //____________________getting all StokeWareHouse under Store and Category____________________________
    @Query("SELECT * FROM stocking_ware_house")
    fun getAllStokeWareHouse(): Flow<List<StockingHouse>>

    @Query("SELECT DISTINCT tswh.id ,ts.type_store , tc.category_name , tswh.first_balance FROM type_store ts , categories tc,stocking_ware_house tswh INNER JOIN type_store ON ts.id = tswh.store_id  INNER JOIN categories ON tc.id = tswh.category_id  GROUP BY tswh.id")
    fun getAllStokeWareHouseWitCategoriesAndStores(): Flow<List<ShowStockWare>>
//    @Query("SELECT * FROM categories  inner join stocking_ware_house on categories.id = stocking_ware_house.category_id ")
//    fun getAllStokeWareHouseWitCategories(): kotlinx.coroutines.flow.Flow<List<StockWareWithCategoriesAndStores>>

    //_____________________________Methods Daily movements____________________________
    //___________________________________Add Daily movements____________________________
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyMovement(dailyMovements: DailyMovements)

    //___________________________________Update Daily movements____________________________
    @Query("UPDATE daily_movement SET  permission_id =:permission_id,category_id=:category_id ,store_id =:store_id ,convert_to =:convert_to ,incoming =:incoming, issued =:issued ,updated_at =:updated_at WHERE id =:id")
    suspend fun updateDailyMovement(id: Long,permission_id: Long, category_id: Long, store_id: Long, convert_to: Long, incoming: Int, issued: Int, updated_at: String)

    //___________________________________Delete Daily movements____________________________
    @Query("DELETE FROM daily_movement WHERE id=:id ")
    suspend fun deleteDailyMovement(id: Long)

    //___________________________________Get Daily movements____________________________
    @Query("SELECT DISTINCT tdm.id , tdm.incoming, tdm.issued , tdm.convert_to,ts.type_store , tc.category_name , tp.permission_name FROM permissions tp,type_store ts , categories tc,daily_movement tdm INNER JOIN type_store ON ts.id = tdm.store_id  INNER JOIN categories ON tc.id = tdm.category_id  INNER JOIN permissions ON tp.id = tdm.permission_id  GROUP BY tdm.id")
    fun getAllDailyMovement(): Flow<List<ShowDailyMovements>>
//@Transaction
// @Query("SELECT DISTINCT tdm.id , tdm.incoming, tdm.issued , tdm.convert_to,ts.type_store , tc.category_name , tp.permission_name FROM permissions tp,type_store ts , categories tc,daily_movement tdm INNER JOIN type_store ON ts.id = tdm.store_id  INNER JOIN categories ON tc.id = tdm.category_id  INNER JOIN permissions ON tp.id = tdm.permission_id  GROUP BY tdm.id")
//    fun getAllDailyMovementNew(): Flow<List<ShowDailyMovements>>

    @Query("SELECT SUM(first_balance) FROM stocking_ware_house WHERE category_id =:category_id ANd store_id =:store_id ")
     fun getFirstBalanceString(category_id: Long,store_id: Long): Flow<Int>

    @Query("SELECT SUM(issued) FROM daily_movement WHERE category_id =:category_id ANd store_id =:store_id ")
    fun getIssuedString(category_id: Long,store_id: Long): Flow<Int>


    @Query("SELECT SUM(incoming) FROM daily_movement WHERE category_id =:category_id ANd store_id =:store_id ")
    fun getIncomingString(category_id: Long,store_id: Long): Flow<Int>


    @Query("SELECT SUM(issued) FROM daily_movement WHERE category_id =:category_id ANd convert_to =:convert_to ")
    fun getIssuedConvertToString(category_id: Long,convert_to: Long): Flow<Int>
}