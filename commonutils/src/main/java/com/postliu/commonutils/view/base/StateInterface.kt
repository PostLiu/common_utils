package com.postliu.commonutils.view.base

import androidx.lifecycle.LifecycleCoroutineScope

interface StateInterface {

    suspend fun launchWhenStarted()

    suspend fun repeatOnLifecycleStarted()

    fun LifecycleCoroutineScope.onState()
}