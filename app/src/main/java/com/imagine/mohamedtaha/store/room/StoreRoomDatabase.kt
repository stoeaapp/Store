package com.imagine.mohamedtaha.store.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.imagine.mohamedtaha.store.room.data.ItemStore
import com.imagine.mohamedtaha.store.room.data.StockingHouse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(ItemStore::class,StockingHouse::class),version = 2,exportSchema = true)
public abstract class StoreRoomDatabase:RoomDatabase() {
    abstract fun storeDao():StoreDao
    companion object{
        //Singleton prevents multiple instances o database opening at the same time.
        @Volatile
        private var INSTANCE: StoreRoomDatabase?= null
        fun getDatabase(context: Context,scope:CoroutineScope):StoreRoomDatabase{
            //if the INSTANCE is not null, then return it, if it is, then create the database
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,
                StoreRoomDatabase::class.java,"store_database")
                        .fallbackToDestructiveMigration()
                      //  .addCallback(StoredatabaseCallback(scope))
                        .build()
                INSTANCE = instance
                //return instance
                instance
            }
        }
    }
    private class StoredatabaseCallback(private val scope:CoroutineScope):RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database->
                scope.launch {
                  //  populateDatabase(database.storeDao())
//                    val storeDao = database.storeDao()
//                    val itemStore = ItemStore(1,"اذن صرف","Paper","no","المخزن الرئيسي","Mohamed",
//                            "30/30/2020","10:30","","")
//                    storeDao.insertStokeWarehouse(itemStore)
                }
            }
        }

//        private fun populateDatabase(storeDao: StoreDao) {
//
//            //Delete all content here.
//           // storeDao.deleteAll()
//            var itemStore = ItemStore(1,"اذن صرف","Paper","no","المخزن الرئيسي","Mohamed",
//            "30/30/2020","10:30","","")
//            storeDao.insertStokeWarehouse(itemStore)
//
//        }
    }
}




