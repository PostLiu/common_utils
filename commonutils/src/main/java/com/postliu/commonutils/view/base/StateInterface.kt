package com.postliu.commonutils.view.base

import androidx.lifecycle.LifecycleCoroutineScope

interface StateInterface {

    suspend fun onceRequest()

    suspend fun autoRefresh()

    fun LifecycleCoroutineScope.onState()
}