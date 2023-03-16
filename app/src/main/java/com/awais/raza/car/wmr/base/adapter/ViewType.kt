package com.awais.raza.car.wmr.base.adapter

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding

interface ViewType<out E> {
    @LayoutRes
    fun layoutId(): Int
    fun data(): E
    fun isUserInteractionEnabled() = true
    fun bind(bi: ViewDataBinding, position: Int, onClickListener: OnItemClickListener<*>)
}
