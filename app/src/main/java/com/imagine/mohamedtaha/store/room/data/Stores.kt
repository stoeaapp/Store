package com.imagine.mohamedtaha.store.room.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "type_store")
data class Stores(@ColumnInfo(name = "type_store")val typeStore:String, @ColumnInfo(name = "notes")val notes:String,
                  @ColumnInfo(name = "created_at")val createdAt:String, @ColumnInfo(name = "time")val time:String) {
    @PrimaryKey(autoGenerate = true)var id:Long? = null
}