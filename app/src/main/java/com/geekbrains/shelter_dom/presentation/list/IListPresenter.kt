package com.geekbrains.shelter_dom.presentation.list

interface IListPresenter<V> {
    var itemClickListener: ((V) -> Unit)?
    var onLongClickListener: ((V) -> Unit)?
    fun bindView(view: V)
    fun getCount(): Int
}