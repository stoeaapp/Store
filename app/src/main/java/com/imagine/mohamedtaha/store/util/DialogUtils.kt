package com.imagine.mohamedtaha.store.util

import android.content.Context
import android.content.DialogInterface
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.imagine.mohamedtaha.store.R

object  DialogUtils {
    @JvmStatic
     fun showMessageWithYesNoMaterialDesign(context: Context, title: String?, message: String?, clickListener: DialogInterface.OnClickListener?) {
        val builder = MaterialAlertDialogBuilder(context, R.style.CustomMaterialDialog)
        builder.setMessage(message)
        builder.setTitle(title)
        builder.setPositiveButton(context.getString(R.string.yes), clickListener)
        builder.setNegativeButton(context.getString(R.string.no), null)
        val dialog = builder.create()
        dialog.show()
    }
}