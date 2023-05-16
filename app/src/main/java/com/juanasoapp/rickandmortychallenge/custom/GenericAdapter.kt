package com.juanasoapp.rickandmortychallenge.custom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.juanasoapp.rickandmortychallenge.R

abstract class GenericAdapter<T>(var listItems: ArrayList<T>, var layoutId: Int, var minimumAmountToReload: Int = 5) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun setItems(newItems: ArrayList<T>) {
        listItems=newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return getViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as Binder<T>).bind(listItems[position])
        if (position == listItems.size - minimumAmountToReload){
            onLoadMoreItems()
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun getItemViewType(position: Int): Int {
        return layoutId
    }

    open fun onLoadMoreItems(){}

    abstract fun getViewHolder(
        viewDataBinding: ViewDataBinding
    ): RecyclerView.ViewHolder

    internal interface Binder<T> {
        fun bind(data: T)
    }
}

