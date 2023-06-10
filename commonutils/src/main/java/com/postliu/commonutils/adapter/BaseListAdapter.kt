package com.postliu.commonutils.adapter

import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.BindingViewHolder

abstract class BaseListAdapter<T : Any, VH : RecyclerView.ViewHolder>(diffCallback: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, VH>(diffCallback), IAdapter<T> {

    private var recyclerViewOrNull: RecyclerView? = null

    private var onItemClickListener: OnItemClickListener<T>? = null

    private var onItemLongClickListener: OnItemLongClickListener<T>? = null

    override val recyclerView: RecyclerView
        get() {
            checkNotNull(recyclerViewOrNull) {
                "Please get it after onAttachedToRecyclerView"
            }
            return recyclerViewOrNull!!
        }

    override val context: Context
        get() = recyclerView.context

    override fun onBindViewHolder(holder: VH, position: Int) {
        val data = getItem(position)
        with(holder.itemView) {
            setOnClickListener { onItemClickListener?.invoke(data, position) }
            setOnLongClickListener { onItemLongClickListener?.invoke(data, position) ?: false }
        }
        onBindViewHolder(holder, data, position)
    }

    abstract fun onBindViewHolder(holder: VH, data: T, position: Int)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerViewOrNull = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        recyclerViewOrNull = null
    }

    override fun setOnItemClickListener(onItemClickListener: OnItemClickListener<T>) {
        this.onItemClickListener = onItemClickListener
    }

    override fun setOnItemLongClickListener(onItemLongClickListener: OnItemLongClickListener<T>) {
        this.onItemLongClickListener = onItemLongClickListener
    }
}

abstract class BaseBindingAdapter<T : Any, VB : ViewBinding>(diffCallback: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, BindingViewHolder<VB>>(diffCallback), ViewBindingIAdapter<T, VB> {

    private var recyclerViewOrNull: RecyclerView? = null

    private var onItemClickListener: OnItemClickListener<T>? = null

    private var onItemLongClickListener: OnItemLongClickListener<T>? = null

    private var onItemChildWithViewBindingEventListener: OnItemChildWithViewBindingEventListener<T, VB>? = null

    override val recyclerView: RecyclerView
        get() {
            checkNotNull(recyclerViewOrNull) {
                "Please get it after onAttachedToRecyclerView"
            }
            return recyclerViewOrNull!!
        }

    override val context: Context
        get() = recyclerView.context

    override fun onBindViewHolder(holder: BindingViewHolder<VB>, position: Int) {
        val data = getItem(position)
        with(holder.itemView) {
            setOnClickListener { onItemClickListener?.invoke(data, position) }
            setOnLongClickListener { onItemLongClickListener?.invoke(data, position) ?: false }
        }
        onItemChildWithViewBindingEventListener?.invoke(holder.binding, data, position)
        onBindViewHolder(holder, data, position)
    }

    abstract fun onBindViewHolder(holder: BindingViewHolder<VB>, data: T, position: Int)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerViewOrNull = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        recyclerViewOrNull = null
    }

    override fun setOnItemClickListener(onItemClickListener: OnItemClickListener<T>) {
        this.onItemClickListener = onItemClickListener
    }

    override fun setOnItemLongClickListener(onItemLongClickListener: OnItemLongClickListener<T>) {
        this.onItemLongClickListener = onItemLongClickListener
    }

    override fun setOnItemChildEventListener(onItemChildWithViewBindingEventListener: OnItemChildWithViewBindingEventListener<T, VB>) {
        this.onItemChildWithViewBindingEventListener = onItemChildWithViewBindingEventListener
    }

}