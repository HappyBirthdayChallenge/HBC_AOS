package com.inha.hbc.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inha.hbc.R
import com.inha.hbc.databinding.ItemUserListBinding
import com.inha.hbc.data.remote.resp.main.Result
import com.inha.hbc.util.fragmentmanager.MainFragmentManager

class MainSearchRVAdapter(var dataArr: List<Result>): RecyclerView.Adapter<MainSearchRVAdapter.UserHolder>() {
    interface SetMainSearch{
        fun onClick(pos: Int)
    }
    lateinit var setMainSearch: SetMainSearch
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val binding = ItemUserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserHolder(binding, setMainSearch, dataArr)
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.init(position)
    }

    override fun getItemCount(): Int {
        return dataArr.size
    }

    class UserHolder(val binding: ItemUserListBinding, val setMainSearch: SetMainSearch, var dataArr: List<Result>): RecyclerView.ViewHolder(binding.root) {
        fun init(pos: Int) {
            initView(pos)
        }

        fun initView(pos: Int) {
            binding.tvItemUserListId.text = dataArr[pos].member.username
            binding.tvItemUserListName.text = dataArr[pos].member.name


            if (dataArr[pos]!!.follow) {
                binding.tvItemUserListFollow.text = "팔로잉"
                binding.tvItemUserListFollow.setTextColor(
                    MainFragmentManager.baseActivity.applicationContext.resources.getColor(R.color.black, null)
                )
                binding.tvItemUserListFollow.textSize = 14f
                binding.tvItemUserListFollow.background =
                    MainFragmentManager.baseActivity.applicationContext.resources.getDrawable(
                        R.drawable.item_white_following_btn, null
                    )
            }
            else {
                binding.tvItemUserListFollow.text = "팔로우"
                binding.tvItemUserListFollow.background = MainFragmentManager.baseActivity.applicationContext.resources.getDrawable(
                    R.drawable.item_blue_follow_btn, null
                )
            }

            Glide.with(MainFragmentManager.baseActivity.applicationContext)
                .load(dataArr[pos]!!.member.image_url)
                .into(binding.ivItemUserList)

        }
    }

}