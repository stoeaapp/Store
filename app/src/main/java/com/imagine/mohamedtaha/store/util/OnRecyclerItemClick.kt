package com.imagine.mohamedtaha.store.util

import android.view.View

interface OnRecyclerItemClick<T> {
fun onClick(t:T,view:View)
}