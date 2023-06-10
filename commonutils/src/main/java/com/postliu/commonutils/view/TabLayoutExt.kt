package com.postliu.commonutils.view

import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.setCustomView
import com.google.android.material.tabs.TabLayout

inline fun <reified VB : ViewBinding> TabLayout.newTabCustomView(block: VB.() -> Unit): TabLayout.Tab {
    return newTab().apply {
        setCustomView(block)
    }
}

inline fun <reified T> TabLayout.createTab(
    tabs: List<T>,
    defaultIndex: Int = 0,
    tab: TabLayout.Tab.(T) -> TabLayout.Tab,
    crossinline onTabUnselected: TabLayout.Tab.(position: Int) -> Unit = {},
    crossinline onTabReselected: TabLayout.Tab.(position: Int) -> Unit = {},
    crossinline onTabSelected: TabLayout.Tab.(position: Int) -> Unit = {}
) {
    if (tabs.isEmpty()) {
        return
    }
    tabs.map {
        tab.invoke(newTab(), it)
    }.forEach {
        addTab(it)
    }
    post { getTabAt(defaultIndex)?.select() }
    addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) = onTabSelected(tab, tab.position)

        override fun onTabUnselected(tab: TabLayout.Tab) = onTabUnselected(tab, tab.position)

        override fun onTabReselected(tab: TabLayout.Tab) = onTabReselected(tab, tab.position)
    })
}