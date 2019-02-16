package com.alien.base.event

import android.arch.lifecycle.LifecycleOwner
import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.util.concurrent.ConcurrentHashMap

class RxBus {

    private var mBus: Subject<Any> = PublishSubject.create()
    private var mStickyEventMap: MutableMap<Class<*>, Any> = ConcurrentHashMap()

    companion object {
        @Volatile
        private var mInstance: RxBus? = null

        fun getInstance(): RxBus{
            if(mInstance == null){
                synchronized(RxBus::class.java){
                    if (mInstance == null){
                        mInstance = RxBus()
                    }
                }
            }
            return mInstance!!
        }
    }

    fun post(event: Any){
        mBus.onNext(event)
    }

    fun hasObservers() = mBus.hasObservers()

    fun <T> toObservable(owner: LifecycleOwner, eventType: Class<T>): Observable<T>{
        val provider = AndroidLifecycle.createLifecycleProvider(owner)
        return mBus.ofType(eventType).compose(provider.bindToLifecycle()).observeOn(AndroidSchedulers.mainThread())
    }

    fun reset(){
        mInstance = null
    }

    fun postStickyEvent(event: Any){
        synchronized (mStickyEventMap) {
            mStickyEventMap.put(event.javaClass, event)
        }
        post(event)
    }

    fun <T> toObservableSticky(owner: LifecycleOwner, eventType: Class<T>): Observable<T> {
        synchronized(mStickyEventMap) {
            val provider = AndroidLifecycle.createLifecycleProvider(owner)
            val observable = mBus.ofType(eventType).compose(provider.bindToLifecycle())
            observable.observeOn(AndroidSchedulers.mainThread())

            val event = mStickyEventMap[eventType]
            return if (event != null){
                observable.mergeWith(Observable.create {
                    it.onNext(eventType.cast(event))
                })
            }else{
                observable
            }
        }
    }

    fun <T> getStickyEvent(eventType: Class<T> ): T {
        synchronized (mStickyEventMap) {
            return eventType.cast(mStickyEventMap[eventType])
        }
    }


    fun <T> removeStickyEvent(eventType : Class<T>): T {
        synchronized (mStickyEventMap) {
            return eventType.cast(mStickyEventMap.remove(eventType))
        }
    }

    fun removeAllStickyEvents() {
        synchronized (mStickyEventMap) {
            mStickyEventMap.clear()
        }
    }

}