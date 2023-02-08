package com.postliu.commonutils

import android.os.Bundle
import androidx.lifecycle.LifecycleCoroutineScope
import com.postliu.commonutils.databinding.ActivityMainBinding
import com.postliu.commonutils.utils.MapNavigationUtils
import com.postliu.commonutils.utils.MapOpenFailed
import com.postliu.commonutils.utils.MapOpenSuccess
import com.postliu.commonutils.view.base.activity.BaseViewBindingActivity
import com.postliu.commonutils.view.copyClip
import com.postliu.commonutils.view.toast

class MainActivity : BaseViewBindingActivity<ActivityMainBinding>() {

    private val mainAdapter by lazy { MainAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            recyclerview.adapter = mainAdapter.also { adapter ->
                adapter.submitList(listOf("1", "2", "3", "4", "5"))
                adapter.setOnItemClickListener { _, position ->
                    toast("整体点击事件：$position")
                    copyClip { "这是测试文本" }
                }
                adapter.setOnItemLongClickListener { _, position ->
                    toast("整体长按事件：$position")
                    return@setOnItemLongClickListener true
                }
                adapter.setOnItemChildEventListener { _, position ->
                    itemImage.setOnClickListener {
                        toast("图片点击：$position")
                    }
                    itemButton.setOnClickListener {
                        when (position) {
                            0 -> {
                                MapNavigationUtils.openBaiduMap(
                                    this@MainActivity,
                                    "22.988558",
                                    "113.269325",
                                    MapOpenFailed {
                                        toast(it.message)
                                    },
                                    MapOpenSuccess {
                                        toast("success")
                                    })
                            }

                            1 -> {
                                MapNavigationUtils.openGaodeMap(
                                    this@MainActivity,
                                    "22.988558",
                                    "113.269325",
                                    MapOpenFailed {
                                        toast(it.message)
                                    },
                                    MapOpenSuccess {
                                        toast("success")
                                    })
                            }

                            2 -> {
                                MapNavigationUtils.openTencentMap(
                                    this@MainActivity,
                                    "22.988558",
                                    "113.269325",
                                    MapOpenFailed {
                                        toast(it.message)
                                    },
                                    MapOpenSuccess {
                                        toast("success")
                                    })
                            }
                        }

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