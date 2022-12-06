package com.postliu.commonutils.view

import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.setCustomView
import com.google.android.material.tabs.TabLayout

inline fun <reified VB : ViewBinding> TabLayout.newTabCustomView(block: VB.() -> Unit): TabLayout.Tab {
    return newTab().apply {
        setCustomView(block)
    }
}