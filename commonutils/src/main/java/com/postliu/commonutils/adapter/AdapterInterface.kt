package com.postliu.commonutils.adapter

import android.content.Context
import android.content.res.Resources.Theme
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

interface ViewBindingAdapterInterface<T, VB : ViewBinding> : AdapterInterface<T> {

    fun setOnItemChildEventListener(onItemChildWithViewBindingEventListener: OnItemChildWithViewBindingEventListener<T, VB>)
}

interface AdapterInterface<T> {
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener<T>)

    fun setOnItemLongClickListener(onItemLongClickListener: OnItemLongClickListener<T>)

    val recyclerView: RecyclerView

    val context: Context

    fun getColor(@ColorRes color: Int, theme: Theme) = ResourcesCompat.getColor(context.resources, color, theme)

    fun getColor(@ColorRes color: Int) = ResourcesCompat.getColor(context.resources, color, null)

    fun getString(@StringRes string: Int) = context.getString(string)

    fun getString(@StringRes string: Int, vararg formatArgs: Any) = context.getString(string, formatArgs)

}