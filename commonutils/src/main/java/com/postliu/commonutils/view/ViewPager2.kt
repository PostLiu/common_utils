package com.postliu.commonutils.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

inline fun <reified A : FragmentActivity> createViewPager2Adapter(
    fragmentActivity: A,
    itemCount: Int,
    crossinline block: (position: Int) -> Fragment
) = object : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = itemCount

    override fun createFragment(position: Int): Fragment = block.invoke(position)
}

inline fun <reified F : Fragment> createViewPager2Adapter(
    fragment: F,
    itemCount: Int,
    crossinline block: (position: Int) -> Fragment
) = object : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = itemCount

    override fun createFragment(position: Int): Fragment = block.invoke(position)
}

inline fun createViewPager2Adapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    itemCount: Int,
    crossinline block: (position: Int) -> Fragment
) = object : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = itemCount

    override fun createFragment(position: Int): Fragment = block.invoke(position)
}