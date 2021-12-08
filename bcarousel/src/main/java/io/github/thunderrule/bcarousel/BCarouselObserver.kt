package io.github.thunderrule.bcarousel

/**
 *  功能描述：
 *  生命周期回调
 *
 * @date 2021/12/6
 */
interface BCarouselObserver {
    fun onStart()
    fun onStop()
    fun onDestroy()
}