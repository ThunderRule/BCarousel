package io.github.thunderrule.bcarousel

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

/**
 *  功能描述：
 *  过渡时间控制
 *
 * @date 2021/12/2
 */
class SpeedManager :LinearLayoutManager {
    private var mCarousel:BCarousel

    constructor(carousel:BCarousel, layoutManager: LinearLayoutManager):super(carousel.context,layoutManager.orientation,false){
        mCarousel = carousel
    }

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView?,
        state: RecyclerView.State?,
        position: Int
    ) {
        val linearSmoothScroller = object : LinearSmoothScroller(recyclerView?.context) {
            override fun calculateTimeForDeceleration(dx: Int): Int {
                return mCarousel.getTurnPageTime()
            }
        }

        linearSmoothScroller.targetPosition = position
        startSmoothScroll(linearSmoothScroller)
    }

    companion object{
        @JvmStatic
        fun changeSmoothTime(carousel: BCarousel){
            if (carousel.getTurnPageTime() < 100){
                return
            }
            try {
                val viewpager = carousel.getViewPager2()
                val recyclerView = viewpager?.getChildAt(0) as RecyclerView
                recyclerView.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                val speedManager = SpeedManager(carousel, recyclerView.layoutManager as LinearLayoutManager)
                recyclerView.layoutManager = speedManager

                val layoutmangerField = ViewPager2::class.java.getDeclaredField("mLayoutManager")
                layoutmangerField.isAccessible = true
                layoutmangerField.set(viewpager,speedManager)

                val pageTransformerAdapterField =
                    ViewPager2::class.java.getDeclaredField("mPageTransformerAdapter")
                pageTransformerAdapterField.isAccessible = true
                val mPageTransformerAdapter = pageTransformerAdapterField[viewpager]
                if (mPageTransformerAdapter != null) {
                    val aClass: Class<*> = mPageTransformerAdapter.javaClass
                    val layoutManager = aClass.getDeclaredField("mLayoutManager")
                    layoutManager.isAccessible = true
                    layoutManager[mPageTransformerAdapter] = speedManager
                }
                val scrollEventAdapterField =
                    ViewPager2::class.java.getDeclaredField("mScrollEventAdapter")
                scrollEventAdapterField.isAccessible = true
                val mScrollEventAdapter = scrollEventAdapterField[viewpager]
                if (mScrollEventAdapter != null) {
                    val aClass: Class<*> = mScrollEventAdapter.javaClass
                    val layoutManager = aClass.getDeclaredField("mLayoutManager")
                    layoutManager.isAccessible = true
                    layoutManager[mScrollEventAdapter] = speedManager
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

}