package com.inha.hbc.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inha.hbc.R
import com.inha.hbc.databinding.ItemUserListBinding
import com.inha.hbc.data.remote.resp.main.Result
import com.inha.hbc.ui.menu.view.AddFriendView
import com.inha.hbc.util.fragmentmanager.MainFragmentManager
import com.inha.hbc.util.network.menu.MenuRetrofitService

class MainSearchRVAdapter(var data: ArrayList<Result>): RecyclerView.Adapter<MainSearchRVAdapter.UserHolder>() {
    interface SetMainSearch{
        fun onClick(pos: Int)
    }
    lateinit var setMainSearch: SetMainSearch
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val binding = ItemUserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserHolder(binding, setMainSearch, data)
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.init(position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class UserHolder(val binding: ItemUserListBinding, val setMainSearch: SetMainSearch, var data: ArrayList<Result>):
        RecyclerView.ViewHolder(binding.root), AddFriendView {
        fun init(pos: Int) {
            initView(pos)
            initListener(pos)
        }

        fun initView(pos: Int) {
            binding.tvItemUserListId.text = data[pos].member.username
            binding.tvItemUserListName.text = data[pos].member.name


            if (data[pos]!!.follow) {
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
                .load(data[pos]!!.member.image_url)
                .into(binding.ivItemUserList)

        }

        fun initListener(pos: Int) {
            binding.tvItemUserListFollow.setOnClickListener {
                if (binding.tvItemUserListFollow.text == "팔로잉") {
                }
                else {
                    MenuRetrofitService().addFriend(data[pos]!!.member.id.toString(), this)
                }
            }

            binding.root.setOnClickListener {
                setMainSearch.onClick(pos)
            }
        }

        override fun onAddFriendSuccess() {
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

        override fun onAddFriendFailure() {
            TODO("Not yet implemented")
        }
    }

}