package com.imagine.mohamedtaha.store.room.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class Users(@ColumnInfo(name = "user_name")val userName:String,val password:String, @ColumnInfo(name="retry_password")val retryPassword:String,
           val email:String,val notes:String, @ColumnInfo(name ="created_at")val createdAt:String,val time:String) {
    @PrimaryKey(autoGenerate = true)var id:Int?= null
}