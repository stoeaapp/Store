package com.imagine.mohamedtaha.store.room.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Categories(@ColumnInfo(name = "category_name")val categoryName:String,@ColumnInfo(name = "natural_category")val naturalCategory:String,
                 @ColumnInfo(name = "notes")val notes:String,@ColumnInfo(name = "created_at")val createdAt:String,val time:String) {
    @PrimaryKey(autoGenerate = true)var id:Long? = null
}