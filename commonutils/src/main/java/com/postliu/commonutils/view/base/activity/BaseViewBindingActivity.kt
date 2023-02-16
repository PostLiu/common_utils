package com.postliu.commonutils.view.base.activity

import android.os.Bundle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.base.ActivityBinding
import com.dylanc.viewbinding.base.ActivityBindingDelegate

abstract class BaseViewBindingActivity<VB : ViewBinding> : BaseActivity(),
    ActivityBinding<VB> by ActivityBindingDelegate() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentViewWithBinding()
    }

    override suspend fun repeatOnLifecycleStarted() {

    }

    override suspend fun launchWhenStarted() {

    }

    override fun LifecycleCoroutineScope.onState() {

    }
}