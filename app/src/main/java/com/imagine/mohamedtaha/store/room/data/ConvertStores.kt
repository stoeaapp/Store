package com.imagine.mohamedtaha.store.room.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "convert_store")
data class ConvertStores(@ColumnInfo(name = "convert_store") val typeStore: String, @ColumnInfo(name = "notes")val notes:String) {
    @PrimaryKey(autoGenerate = true)var id:Long? = null
    @ColumnInfo(name = "created_at")var createdAt:String?=null
    @ColumnInfo(name = "time")var time:String?=null
    @ColumnInfo(name = "updated_at")var updatedAt:String?= null
}