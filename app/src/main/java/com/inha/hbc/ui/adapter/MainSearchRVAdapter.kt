package com.inha.hbc.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inha.hbc.databinding.ItemUserListBinding
import com.inha.hbc.data.remote.resp.main.Result

class MainSearchRVAdapter(var dataArr: List<Result>): RecyclerView.Adapter<MainSearchRVAdapter.UserHolder>() {
    interface SetMainSearch{
        fun onClick(pos: Int)
    }
    lateinit var setMainSearch: SetMainSearch
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class UserHolder(val binding: ItemUserListBinding): RecyclerView.ViewHolder(binding.root) {

    }

}