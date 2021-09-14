package com.imagine.mohamedtaha.store.room.data

import androidx.room.ColumnInfo

data class ShowDailyMovements(@ColumnInfo(name = "id") var id: String?, @ColumnInfo(name = "category_name") var categoryName: String?,
                              @ColumnInfo(name = "permission_name") var permissionName: String?, @ColumnInfo(name = "type_store") var typeStore: String?,
                              @ColumnInfo(name = "incoming") val incoming: Int?, @ColumnInfo(name = "issued") val issued: Int?,
                              @ColumnInfo(name = "convert_to") val convertTo: String?, @ColumnInfo(name = "notes") var notes: String?,
                              @ColumnInfo(name = "created_at") var createdAt: String?, @ColumnInfo(name = "time") var time: String?,
                              @ColumnInfo(name = "updated_at") var updatedAt: String?)