//package com.postliu.commonutils
//
//import android.view.ViewGroup
//import androidx.recyclerview.widget.DiffUtil
//import com.dylanc.viewbinding.BindingViewHolder
//import com.postliu.commonutils.adapter.BaseBindingAdapter
//import com.postliu.commonutils.databinding.ItemMainLayoutBinding
//
//class MainAdapter : BaseBindingAdapter<String, ItemMainLayoutBinding>(MainDiffCallback()) {
//
//    override fun onBindViewHolder(holder: BindingViewHolder<ItemMainLayoutBinding>, data: String, position: Int) {
//        holder.binding.apply {
//            itemText.text = data
//            itemText.setTextColor(getColor(R.color.purple_200))
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<ItemMainLayoutBinding> {
//        return BindingViewHolder(parent)
//    }
//
//    class MainDiffCallback : DiffUtil.ItemCallback<String>() {
//        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
//            return oldItem.hashCode() == newItem.hashCode()
//        }
//
//        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
//            return oldItem == newItem
//        }
//    }
//}