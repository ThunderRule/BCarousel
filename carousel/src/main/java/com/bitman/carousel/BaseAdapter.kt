package com.bitman.carousel

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 *  功能描述：
 *  基础适配器，无限轮播
 *
 * @date 2021/12/6
 */
abstract class BaseAdapter<VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = createViewholder(parent, viewType)

    override fun onBindViewHolder(holder: VH, position: Int) {
        val totalCount = totalCount()
        if (totalCount <= 0) {
            return
        }
        val i = position % totalCount
        bind(holder, i)
    }

    override fun getItemCount(): Int = Int.MAX_VALUE

    abstract fun createViewholder(parent: ViewGroup, viewType: Int): VH

    abstract fun totalCount(): Int

    abstract fun bind(holder: VH, position: Int)

}