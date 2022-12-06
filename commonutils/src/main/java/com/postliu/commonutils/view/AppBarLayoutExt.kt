package com.postliu.commonutils.view

import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

typealias OnStateChange = (AppBarLayout, AppBarLayoutState, verticalOffset: Int) -> Unit

sealed interface AppBarLayoutState {
    object Idle : AppBarLayoutState
    object Expanded : AppBarLayoutState
    object Collapsed : AppBarLayoutState
}

fun AppBarLayout.addOnOffsetStateChangeListener(onStateChange: OnStateChange) {
    var currentState: AppBarLayoutState = AppBarLayoutState.Idle
    addOnOffsetChangedListener { appBarLayout, verticalOffset ->
        if (verticalOffset == 0) {
            if (currentState != AppBarLayoutState.Expanded) {
                onStateChange(appBarLayout, AppBarLayoutState.Expanded, verticalOffset)
            }
            currentState = AppBarLayoutState.Expanded
        } else if (abs(verticalOffset) >= appBarLayout.totalScrollRange) {
            if (currentState != AppBarLayoutState.Collapsed) {
                onStateChange(appBarLayout, AppBarLayoutState.Collapsed, verticalOffset)
            }
            currentState = AppBarLayoutState.Collapsed
        } else {
            if (currentState != AppBarLayoutState.Idle) {
                onStateChange(appBarLayout, AppBarLayoutState.Idle, verticalOffset)
            }
            currentState = AppBarLayoutState.Idle
        }
    }
}