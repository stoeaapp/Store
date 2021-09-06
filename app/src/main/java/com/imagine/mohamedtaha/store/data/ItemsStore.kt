package com.imagine.mohamedtaha.store.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by MANASATT on 03/12/17.
 */
public class ItemsStore {
    var id:Int?= null
    var namePermission:String? = null
    var typeStore: String? = null
    var notes: String? = null
    var createdDate: String? = null
    var createdTime: String? = null
    var convertTo: String? = null
    var nameGategory: String? = null
    var nauralCategory: String? = null
    var userName: String? = null
    fun getmTimeInMilliseconds(): Long {
        return mTimeInMilliseconds
    }

    fun setmTimeInMilliseconds(mTimeInMilliseconds: Long) {
        this.mTimeInMilliseconds = mTimeInMilliseconds
    }

    private var mTimeInMilliseconds: Long = 0
    var issued = 0
    var incoming = 0
    var id_code_category: Long = 0
    var id_code_store: Long = 0
    var id_permission_id: Long = 0
    var id_convert_to: Long = 0
    var userId: Long = 0
    var first_balanse = 0
}