package com.juanasoapp.rickandmortychallenge.custom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.juanasoapp.rickandmortychallenge.R

abstract class GenericAdapter<T>(var listItems: List<T>, var layoutId: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun setItems(listItems: List<T>) {
        this.listItems = listItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return getViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as Binder<T>).bind(listItems[position])
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun getItemViewType(position: Int): Int {
        return layoutId
    }

    abstract fun getViewHolder(
        viewDataBinding: ViewDataBinding
    ): RecyclerView.ViewHolder

    internal interface Binder<T> {
        fun bind(data: T)
    }
}
