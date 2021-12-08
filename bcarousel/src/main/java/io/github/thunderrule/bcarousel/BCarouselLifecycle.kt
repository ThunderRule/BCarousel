package io.github.thunderrule.bcarousel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 *  功能描述：
 *
 *
 * @date 2021/12/6
 */
class BCarouselLifecycle(var mObserver: BCarouselObserver) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart(){
        mObserver.onStart()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop(){
        mObserver.onStop()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestory(){
        mObserver.onDestroy()
    }
}