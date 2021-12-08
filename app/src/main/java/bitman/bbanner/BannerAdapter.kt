package bitman.bbanner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.github.thunderrule.bcarousel.BaseAdapter

/**
 *  功能描述：
 *
 *
 * @date 2021/12/7
 */
class BannerAdapter(var mContext:Context) : BaseAdapter<BannerAdapter.BannerHolder>() {
    private val mList = arrayListOf<String>()

    inner class BannerHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val mIv = itemView.findViewById<ImageView>(R.id.mIv)
    }

    override fun totalCount(): Int = mList.size

    override fun bind(holder: BannerHolder, position: Int) {
        val imgUrl = mList[position]
        Glide.with(mContext)
            .load(imgUrl)
            .into(holder.mIv)
    }

    override fun createViewholder(parent: ViewGroup, viewType: Int): BannerHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_banner,parent,false)
        return BannerHolder(view)
    }

    fun setData(list:ArrayList<String>){
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }
}