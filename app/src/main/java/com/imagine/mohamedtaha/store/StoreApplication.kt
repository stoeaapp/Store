package com.imagine.mohamedtaha.store

import android.app.Application
import com.imagine.mohamedtaha.store.room.StoreRepository
import com.imagine.mohamedtaha.store.room.StoreRoomDatabase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
@HiltAndroidApp
class StoreApplication:Application() {
   val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { StoreRoomDatabase.getDatabase(this,applicationScope) }
    val repository by lazy { StoreRepository(database.storeDao()) }
}