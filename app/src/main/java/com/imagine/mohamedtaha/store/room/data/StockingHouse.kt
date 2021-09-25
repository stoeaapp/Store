package com.imagine.mohamedtaha.store.room.data

import androidx.room.*
import java.io.Serializable

@Entity(tableName = "stocking_ware_house", foreignKeys = [ForeignKey(entity = Categories::class, parentColumns = ["id"], childColumns = ["category_id"], onDelete = ForeignKey.RESTRICT),
    ForeignKey(entity = Stores::class, parentColumns = ["id"], childColumns = ["store_id"], onDelete = ForeignKey.RESTRICT)],
        indices = [Index(value = ["category_id"]), Index(value = ["store_id"])])
data class StockingHouse(@ColumnInfo(name = "category_id") val categoryId: Long, @ColumnInfo(name = "store_id") val storeId: Long,
                         @ColumnInfo(name = "first_balance") val firstBalance: String, @ColumnInfo(name = "user_id") val userId: String,
                         @ColumnInfo(name = "notes") val notes: String):Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
    @ColumnInfo(name = "created_at")var createdAt:String?=null
    @ColumnInfo(name = "time")var time:String?=null
    @ColumnInfo(name = "updated_at")var updatedAt:String?= null
}