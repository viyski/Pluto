package com.alien.base.mvp

interface BasePresenter<T: MVPView>{

    fun onAttach(view: T)

    fun onDetach()
}