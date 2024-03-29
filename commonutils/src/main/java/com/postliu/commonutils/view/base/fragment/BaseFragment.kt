package com.postliu.commonutils.view.base.fragment

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.postliu.commonutils.view.base.StateInterface

abstract class BaseFragment : Fragment, StateInterface {

    constructor() : super()

    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewLifecycleOwner.lifecycleScope) {
            launchWhenStarted {
                launchWhenStarted()
            }
            launchWhenStarted {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    repeatOnLifecycleStarted()
                }
            }
            launchWhenStarted {
                onState()
            }
        }
    }
}