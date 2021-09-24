package com.imagine.mohamedtaha.store.room.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "type_store")
data class Stores(@ColumnInfo(name = "type_store")var typeStore:String, @ColumnInfo(name = "notes")var notes:String):Serializable {
    @PrimaryKey(autoGenerate = true)var id:Long? = null
    @ColumnInfo(name = "created_at")var createdAt:String?=null
    @ColumnInfo(name = "time")var time:String?=null
    @ColumnInfo(name = "updated_at")var updatedAt:String?= null
}