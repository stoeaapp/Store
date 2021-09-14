package com.imagine.mohamedtaha.store

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.imagine.mohamedtaha.store.ui.fragments.permissions.EditPermissionFragment

class ResultCallback : ActivityResultContract<String,String>() {
    override fun createIntent(context: Context, input: String?): Intent = Intent(context,EditPermissionFragment::class.java).apply {
        putExtra("item",input)
    }
    override fun parseResult(resultCode: Int, intent: Intent?): String?  = when {
        resultCode != Activity.RESULT_OK ->null
        else -> intent?.getStringExtra("data")
    }
//
//    override fun createIntent(context: Context, input: String?): Intent = Intent(context,EditPermissionFragment().show(fragmentManager,Constant.DIALOG_PERMISSION)).apply {
//        putExtra("item",input)
//    }
}