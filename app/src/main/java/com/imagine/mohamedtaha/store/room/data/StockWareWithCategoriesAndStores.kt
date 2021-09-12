package com.imagine.mohamedtaha.store.room.data

import androidx.room.ColumnInfo

class StockWareWithCategoriesAndStores(@ColumnInfo(name = "id") var id: String, @ColumnInfo(name = "category_name") var categoryName: String,
                                       @ColumnInfo(name = "natural_category") var natural_category: String, @ColumnInfo(name = "type_store") var typeStore: String,
                                       @ColumnInfo(name = "notes") var notes: String, @ColumnInfo(name = "created_at")
                                       var createdAt: String, @ColumnInfo(name = "time")
                                       var time: String, @ColumnInfo(name = "updated_at") var updatedAt: String?, @ColumnInfo(name = "first_balance") var firstBalance: String)


