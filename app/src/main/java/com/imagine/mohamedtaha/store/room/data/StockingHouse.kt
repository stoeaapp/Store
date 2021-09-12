package com.imagine.mohamedtaha.store.room.data

import androidx.room.*

@Entity(tableName = "stocking_ware_house", foreignKeys = [ForeignKey(entity = Categories::class, parentColumns = ["id"], childColumns = ["category_id"], onDelete = ForeignKey.RESTRICT),
    ForeignKey(entity = Stores::class, parentColumns = ["id"], childColumns = ["store_id"], onDelete = ForeignKey.RESTRICT)],
        indices = [Index(value = ["category_id"]),Index(value = ["store_id"])])
data class StockingHouse(@ColumnInfo(name = "category_id") val categoryId: Long, @ColumnInfo(name = "store_id") val storeId: Long,
                         @ColumnInfo(name = "first_balance") val firstBalance: String, @ColumnInfo(name = "user_id") val userId: String,
                         @ColumnInfo(name = "notes") val notes: String, @ColumnInfo(name = "date") val date: String,
                         @ColumnInfo(name = "time") val time: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
}