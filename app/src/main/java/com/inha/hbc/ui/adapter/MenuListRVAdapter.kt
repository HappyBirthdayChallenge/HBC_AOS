package com.inha.hbc.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inha.hbc.R
import com.inha.hbc.databinding.ItemMenuListBinding

class MenuListRVAdapter(val menuList: ArrayList<String>): RecyclerView.Adapter<MenuListRVAdapter.MenuHolder>() {

    interface onListener {
        fun onClick(menu: String)
    }

    lateinit var onlistener: onListener
    lateinit var binding: ItemMenuListBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuHolder {
        binding = ItemMenuListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MenuHolder(binding, menuList, onlistener)
    }

    override fun onBindViewHolder(holder: MenuHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    class MenuHolder(val binding: ItemMenuListBinding, val menuList: ArrayList<String>, val onlistener: onListener): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            binding.tvItemMenuTitle.text = menuList[pos]
            setIcon(binding.tvItemMenuTitle.text.toString())
            initListener(pos)

            initView()
        }

        fun initListener(pos: Int) {
            binding.root.setOnClickListener {
                onlistener.onClick(binding.tvItemMenuTitle.text.toString())
            }
        }


        fun setIcon(menu: String) {
            binding.ivItemMenuIcon.setImageResource(
                when(menu) {
                    "친구목록" -> {
                        R.drawable.ic_friends
                    }
                    "알림함" -> {
                        R.drawable.ic_notify
                    }
                    else -> {
                        R.drawable.ic_close
                    }
                }
            )
        }

        fun initView() {

        }
    }

}