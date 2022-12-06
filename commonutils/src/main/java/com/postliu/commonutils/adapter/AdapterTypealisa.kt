package com.postliu.commonutils.adapter

import android.view.View

typealias OnItemClickListener<T> = (data: T, position: Int) -> Unit

typealias OnItemLongClickListener<T> = (data: T, position: Int) -> Boolean

typealias OnItemChildEventListener<T> = View.(data: T, position: Int) -> Unit

typealias OnItemChildWithViewBindingEventListener<T, VB> = VB.(data: T, position: Int) -> Unit