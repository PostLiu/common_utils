package com.postliu.commonutils.view.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.postliu.commonutils.view.base.StateInterface

abstract class BaseActivity : AppCompatActivity(), StateInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(lifecycleScope) {
            launchWhenStarted {
                launchWhenStarted()
            }
            launchWhenStarted {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    repeatOnLifecycleStarted()
                }
            }
            launchWhenStarted {
                onState()
            }
        }
    }
}