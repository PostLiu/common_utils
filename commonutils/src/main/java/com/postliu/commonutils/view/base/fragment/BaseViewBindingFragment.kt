package com.postliu.commonutils.view.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.base.FragmentBinding
import com.dylanc.viewbinding.base.FragmentBindingDelegate

abstract class BaseViewBindingFragment<VB : ViewBinding> :
    BaseFragment(), FragmentBinding<VB> by FragmentBindingDelegate() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createViewWithBinding(inflater, container)
    }

    override suspend fun launchWhenStarted() {

    }

    override suspend fun repeatOnLifecycleStarted() {

    }

    override fun LifecycleCoroutineScope.onState() {

    }
}