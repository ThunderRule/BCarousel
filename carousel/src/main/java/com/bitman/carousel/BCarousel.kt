package com.bitman.carousel

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import java.lang.ref.WeakReference

/**
 *  功能描述：
 *
 *
 * @date 2021/12/6
 */
class BCarousel : FrameLayout, BCarouselObserver {
    /**
     * 轮播方向：横向
     */
    private val HORIZONTAL = 1

    /**
     * 默认翻页时间间隔3s
     */
    private var mDelayedTime = 3000L

    /**
     * 轮播方向，默认为横向
     */
    private var mOrientation: Int

    /**
     * 翻页过渡时间，默认600ms
     */
    private var mTurnTime: Int

    private var mViewPager: ViewPager2? = null
    private var mTask: BBannerTask? = null
    private var mAdapter: BaseAdapter<*>? = null

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val osa = context.obtainStyledAttributes(attrs,R.styleable.BBanner)
        mOrientation = osa.getInteger(R.styleable.BBanner_orientation, HORIZONTAL)
        mTurnTime = osa.getInteger(R.styleable.BBanner_turnTime, 600)
        osa.recycle()

        init(context)
    }

    private fun init(context: Context) {
        mViewPager = ViewPager2(context)
        mViewPager?.layoutParams =
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        mViewPager?.orientation =
            if (mOrientation == HORIZONTAL) {
                ViewPager2.ORIENTATION_HORIZONTAL
            } else {
                ViewPager2.ORIENTATION_VERTICAL
            }
        SpeedManager.changeSmoothTime(this)
        mTask = BBannerTask(this)

        addView(mViewPager)
    }

    /**
     * 设置适配器
     */
    fun <T : RecyclerView.ViewHolder> setAdapter(adapter: BaseAdapter<T>): BCarousel {
        mViewPager?.adapter = adapter
        mAdapter = adapter
        return this
    }

    /**
     * 设置轮播的指定位置
     */
    fun setCurrentItem(item: Int) {
        mViewPager?.currentItem = item
    }

    /**
     * 设置翻页间隔时间
     */
    fun setDelyedTime(time: Long): BCarousel {
        mDelayedTime = time
        return this
    }

    /**
     * 设置是否禁止手动滑动翻页
     */
    fun setScrollEnable(enable: Boolean): BCarousel {
        mViewPager?.isUserInputEnabled = enable
        return this
    }

    /**
     * 设置生命周期
     */
    fun setLifecycle(owner: LifecycleOwner?): BCarousel {
        owner?.run {
            val bannerLifecycle = BCarouselLifecycle(this@BCarousel)
            lifecycle.addObserver(bannerLifecycle)
        }
        return this
    }

    /**
     * 设置翻页过渡动效
     */
    fun setPageTransformer(transformer:ViewPager2.PageTransformer):BCarousel{
        mViewPager?.setPageTransformer(transformer)
        return this
    }

    /**
     * 获取总页数
     */
    fun getItemCount(): Int {
        return mAdapter?.itemCount ?: 0
    }

    /**
     * 获取当前显示的页数
     */
    fun getCurrentItem(): Int {
        return mViewPager?.currentItem ?: -1
    }

    /**
     * 获取ViewPager2对象
     */
    fun getViewPager2() = mViewPager

    /**
     * 获取翻页过渡时间
     */
    fun getTurnPageTime() = mTurnTime

    override fun onStart() {
        start()
    }

    override fun onStop() {
        stop()
    }

    override fun onDestroy() {
        destroy()
    }

    private fun start() {
        stop()
        postDelayed(mTask, mDelayedTime)
    }

    private fun stop() {
        removeCallbacks(mTask)
    }

    private fun destroy() {
        stop()
    }

    /**
     * 用户滑动时，停止计时；松开时，重新计时
     */
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (mViewPager?.isUserInputEnabled != true){
            return super.dispatchTouchEvent(ev)
        }
        when (ev?.action) {
            MotionEvent.ACTION_UP,MotionEvent.ACTION_CANCEL,MotionEvent.ACTION_OUTSIDE -> start()
            MotionEvent.ACTION_DOWN -> stop()
            else -> {
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private inner class BBannerTask(carousel: BCarousel) : Runnable {
        private val mBanner = WeakReference(carousel)

        override fun run() {
            val banner = mBanner.get()
            if (banner != null) {
                val itemCount = banner.getItemCount()
                if (itemCount <= 0) {
                    return
                }
                var currentItem = banner.getCurrentItem()
                if (currentItem == -1) {
                    return
                }
                banner.setCurrentItem(++currentItem)
                banner.postDelayed(banner.mTask, mDelayedTime)
            }
        }

    }
}