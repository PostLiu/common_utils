package com.postliu.commonutils

import android.os.Bundle
import androidx.lifecycle.LifecycleCoroutineScope
import com.postliu.commonutils.databinding.ActivityMainBinding
import com.postliu.commonutils.view.base.activity.BaseViewBindingActivity
import com.postliu.commonutils.view.copyClip
import com.postliu.commonutils.view.toast

class MainActivity : BaseViewBindingActivity<ActivityMainBinding>() {

    private val mainAdapter by lazy { MainAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            recyclerview.adapter = mainAdapter.also {
                it.submitList(listOf("1", "2", "3", "4", "5"))
                it.setOnItemClickListener { data, position ->
                    toast("整体点击事件：$position")
                    copyClip { "这是测试文本" }
                }
                it.setOnItemLongClickListener { data, position ->
                    toast("整体长按事件：$position")
                    return@setOnItemLongClickListener true
                }
                it.setOnItemChildEventListener { data, position ->
                    itemImage.setOnClickListener {
                        toast("图片点击：$position")
                    }
                    itemButton.setOnClickListener {
                        toast("按钮点击事件：$position")
                    }
                }
            }
        }
    }

    override suspend fun autoRefresh() {

    }

    override suspend fun onceRequest() {

    }

    override fun LifecycleCoroutineScope.onState() {

    }
}