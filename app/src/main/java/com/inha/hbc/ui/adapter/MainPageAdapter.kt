package com.inha.hbc.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.inha.hbc.R
import com.inha.hbc.data.remote.resp.message.RoomInfoSuccess
import com.inha.hbc.databinding.ItemMainPageBinding
import com.inha.hbc.ui.assist.cakeSelectionAssist
import com.inha.hbc.ui.main.view.RoomInfoView
import com.inha.hbc.util.fragmentmanager.MainFragmentManager
import com.inha.hbc.util.network.message.MessageRetrofitService
import com.inha.hbc.util.sharedpreference.GlobalApplication

class MainPageAdapter(var pageData: ArrayList<Int>): RecyclerView.Adapter<MainPageAdapter.PageHolder>() {

    lateinit var binding: ItemMainPageBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageHolder {
        binding = ItemMainPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PageHolder(binding, pageData)
    }

    override fun onBindViewHolder(holder: PageHolder, position: Int) {
        holder.init(position)
    }

    override fun getItemCount(): Int = pageData.size

    class PageHolder(val binding: ItemMainPageBinding, val pageData: ArrayList<Int>): RecyclerView.ViewHolder(binding.root),
        RoomInfoView {

        val bgOptions = RequestOptions().fitCenter()
            .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)

        fun init(pos: Int) {
            initView(pos)
            initObject(pos)
        }
        fun initView(pos: Int) {
            if (pageData[pos] == 0) {
                Glide.with(itemView).load(R.drawable.bg_type1_left).apply(bgOptions).into(binding.ivItemMainBg)
            }
            else if (pos == 1) {
                Glide.with(itemView).load(R.drawable.bg_type1_front).apply(bgOptions).into(binding.ivItemMainBg)
            }
            else if (pos == 2) {
                Glide.with(itemView).load(R.drawable.bg_type1_sub).apply(bgOptions).into(binding.ivItemMainBg)
            }
            else {
                Glide.with(itemView).load(R.drawable.bg_type1_right).apply(bgOptions).into(binding.ivItemMainBg)
            }
        }

        fun initObject(pos: Int) {
            MessageRetrofitService().roomInfo(GlobalApplication.prefs.getInfo()!!.id.toString(), this)
        }

        override fun onRoomInfoSuccess(data: RoomInfoSuccess) {
            MainFragmentManager.roomId = data.data!![0].room_id

            val arr = data.data[0].cake_type.split("E")
            val cakeType = arr[arr.size - 1].toString().toInt()
            val cakeId= cakeSelectionAssist(cakeType)

            binding.ivItemMainCake.setImageResource(cakeId)
        }

        override fun onRoomInfoFailure() {
        }
    }
}
