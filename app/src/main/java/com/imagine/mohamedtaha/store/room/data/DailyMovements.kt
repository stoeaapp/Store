package com.imagine.mohamedtaha.store.room.data

import androidx.room.*
import java.io.Serializable

//@Entity(tableName = "daily_movement")
//data class DailyMovements(@ColumnInfo(name = "category_id") val categoryId: Long, @ColumnInfo(name = "store_id") val storeId: Long,
//                          @ColumnInfo(name = "permission_id") val permissionId: Long, @ColumnInfo(name = "incoming") val incoming: Int,
//                          @ColumnInfo(name = "issued") val issued: Int) {
//    @PrimaryKey(autoGenerate = true)
//    var id: Long? = null
//    @ColumnInfo(name = "convert_to") var convertTo: Long? =null
//    @ColumnInfo(name = "created_at")var createdAt:String?=null
//    @ColumnInfo(name = "time")var time:String?=null
//    @ColumnInfo(name = "updated_at")var updatedAt:String?= null
//    @ColumnInfo(name = "user_id") var userId: String? =null
//}


@Entity(tableName = "daily_movement", foreignKeys = [ForeignKey(entity = Categories::class, parentColumns = ["id"], childColumns = ["category_id"], onDelete = ForeignKey.NO_ACTION),
    ForeignKey(entity = Stores::class, parentColumns = ["id"], childColumns = ["store_id"], onDelete = ForeignKey.NO_ACTION),
    ForeignKey(entity = Stores::class, parentColumns = ["id"], childColumns = ["convert_to"], onDelete = ForeignKey.NO_ACTION),
    ForeignKey(entity = Permissions::class, parentColumns = ["id"], childColumns = ["permission_id"], onDelete = ForeignKey.NO_ACTION)],
        indices = [Index(value = ["category_id"]), Index(value = ["store_id"]), Index(value = ["permission_id"]), Index(value = ["convert_to"])])
data class DailyMovements(@ColumnInfo(name = "category_id") val categoryId: Long, @ColumnInfo(name = "store_id") val storeId: Long,
                          @ColumnInfo(name = "permission_id") val permissionId: Long, @ColumnInfo(name = "incoming") val incoming: Int,
                          @ColumnInfo(name = "issued") val issued: Int):Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
    @ColumnInfo(name = "convert_to") var convertTo: Long? =null
    @ColumnInfo(name = "created_at")var createdAt:String?=null
    @ColumnInfo(name = "time")var time:String?=null
    @ColumnInfo(name = "updated_at")var updatedAt:String?= null
    @ColumnInfo(name = "user_id") var userId: String? =null
}