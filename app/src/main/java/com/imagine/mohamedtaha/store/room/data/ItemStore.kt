package com.imagine.mohamedtaha.store.room.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "stores")
data class ItemStore(@ColumnInfo(name = "name_permission")val namePermission:String,
@ColumnInfo(name = "name_category")val nameCategory:String,
@ColumnInfo(name="name_natural")val nameNatural:String,
                     @ColumnInfo(name = "type_store")val typeStore:String,
                     @ColumnInfo(name = "user_name")val userName:String,
                     @ColumnInfo(name="create_date")val createData:String,
                     @ColumnInfo(name="create_time")val createTime:String,
                     @ColumnInfo(name="convert_to")val convertTo:String,
                     @ColumnInfo(name= "notes")val notes:String) {
   @PrimaryKey(autoGenerate = true)var id:Long? = null

//   @PrimaryKey(autoGenerate = true)val id:Int?= null
//   @ColumnInfo(name = "name_permission")val namePermission:String?= null
//   @ColumnInfo(name = "name_category")val nameCategory:String?= null
//   @ColumnInfo(name="name_natural")val nameNatural:String?= null
//   @ColumnInfo(name = "type_store")val typeStore:String?= null
//   @ColumnInfo(name = "user_name")val userName:String? = null
//   @ColumnInfo(name="create_date")val createData:String?= null
//   @ColumnInfo(name="create_time")val createTime:String?= null
//   @ColumnInfo(name="convert_to")val convertTo:String? = null
//   @ColumnInfo(name= "notes")val notes:String?= null
//
//
   var issued = 0
   var incoming = 0
   var id_code_category: Long = 0
   var id_code_store: Long = 0
   var id_permission_id: Long = 0
   var id_convert_to: Long = 0
   var userId: Long = 0
   var first_balanse = 0
   var notes_ :String = ""



}

