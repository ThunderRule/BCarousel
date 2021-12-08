package bitman.bbanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import io.github.thunderrule.bcarousel.BCarousel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mHBanner = findViewById<BCarousel>(R.id.mHBanner)
        val mVBanner = findViewById<BCarousel>(R.id.mVBanner)
        val mLBanner = findViewById<BCarousel>(R.id.mLBanner)

        val adapter = BannerAdapter(this)

        val transformer = ViewPager2.PageTransformer { page, position ->
            run {
                if (position >= -1.0f && position <= 0.0f) {
                    page.scaleX = 1 + position * 0.1f
                    page.scaleY = 1 + position * 0.2f
                } else if (position > 0.0f && position < 1.0f) {
                    page.scaleX = 1 - position * 0.1f
                    page.scaleY = 1 - position * 0.2f
                } else {
                    page.scaleX = 0.9f
                    page.scaleY = 0.8f
                }
            }
        }

        mHBanner.setAdapter(adapter)
            .setLifecycle(this)

        mVBanner.setAdapter(adapter)
            .setLifecycle(this)
            .setPageTransformer(MarginPageTransformer(20))

        mLBanner.setAdapter(adapter)
            .setLifecycle(this)
            .setPageTransformer(transformer)


        adapter.setData(
            arrayListOf(
                "https://images.pexels.com/photos/8505220/pexels-photo-8505220.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260",
                "https://images.pexels.com/photos/7891011/pexels-photo-7891011.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260",
                "https://images.pexels.com/photos/5346314/pexels-photo-5346314.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260",
                "https://images.pexels.com/photos/8975677/pexels-photo-8975677.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260"
            )
        )
    }
}