package com.inha.hbc.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inha.hbc.data.remote.resp.menu.GetProfileSuccess
import com.inha.hbc.databinding.ItemMenuPartyroomBinding

class MenuPartyroomRVAdapter(val data: GetProfileSuccess): RecyclerView.Adapter<MenuPartyroomRVAdapter.PartyroomHolder>() {
    interface SetPartyroomRv{
        fun onClick(pos: Int)
    }
    lateinit var setPartyroomRv: SetPartyroomRv
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartyroomHolder {
        val binding = ItemMenuPartyroomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PartyroomHolder(binding, setPartyroomRv)
    }

    override fun onBindViewHolder(holder: PartyroomHolder, position: Int) {
        holder.init(position)
    }

    override fun getItemCount(): Int {
        return data.data.rooms.size
    }

    class PartyroomHolder(val binding: ItemMenuPartyroomBinding, val setPartyroomRv: SetPartyroomRv): RecyclerView.ViewHolder(binding.root) {
        fun init(pos: Int) {
            initListener(pos)
            initView(pos)
        }
        fun initView(pos: Int) {
            binding.tvMenuPartyroom.text= "year"
        }

        fun initListener(pos: Int) {
            binding.root.setOnClickListener {
                setPartyroomRv.onClick(pos)
            }
        }
    }

}
