package com.inha.hbc.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inha.hbc.data.remote.resp.menu.GetProfileSuccess
import com.inha.hbc.databinding.ItemMenuPartyroomBinding
import com.inha.hbc.ui.assist.cakeSelectionAssist
import com.inha.hbc.ui.assist.selectionCandle
import com.inha.hbc.util.fragmentmanager.MainFragmentManager
import java.util.*

class MenuPartyroomRVAdapter(val data: GetProfileSuccess): RecyclerView.Adapter<MenuPartyroomRVAdapter.PartyroomHolder>() {
    interface SetPartyroomRv{
        fun onClick(pos: Int)
    }
    lateinit var setPartyroomRv: SetPartyroomRv
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartyroomHolder {
        val binding = ItemMenuPartyroomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PartyroomHolder(binding, setPartyroomRv, data)
    }

    override fun onBindViewHolder(holder: PartyroomHolder, position: Int) {
        holder.init(position)
    }

    override fun getItemCount(): Int {
        return data.data.rooms.size
    }

    class PartyroomHolder(val binding: ItemMenuPartyroomBinding, val setPartyroomRv: SetPartyroomRv, var data: GetProfileSuccess): RecyclerView.ViewHolder(binding.root) {
        fun init(pos: Int) {
            initListener(pos)
            initView(pos)
        }
        fun initView(pos: Int) {
            binding.tvMenuPartyroom.text= data.data.rooms[pos].birth_date.year.toString()
            val arr = data.data.rooms[pos].cake_type.split("E")
            val cakeType = arr[arr.size - 1].toInt()

            binding.ivItemMenuPartyroomCake.setImageResource(cakeSelectionAssist(cakeType))
            val date = data.data.member.birth_date.date
            val month = data.data.member.birth_date.month

            val cal = Calendar.getInstance()
            val mon = cal.get(Calendar.MONTH) + 1
            val day = cal.get(Calendar.DATE)

            var isBirth = false
            if (mon == month && date== day) {
                isBirth = true
            }

            val birth =  cal.get(Calendar.YEAR) - data.data.member.birth_date.year + 1
            var birthString = ""
            if (birth < 10) {
                birthString = "0$birth"
            }
            else {
                birthString = "$birth"
            }
            if (isBirth) {
                binding.ivItemMenuPartyroomFireCandleFirst.visibility = View.VISIBLE
                binding.ivItemMenuPartyroomFireCandleLast.visibility = View.VISIBLE
                binding.ivItemMenuPartyroomFireCandleFirst.setImageResource(selectionCandle(birthString[0].toString().toInt(), isBirth))
                binding.ivItemMenuPartyroomFireCandleLast.setImageResource(selectionCandle(birthString[1].toString().toInt(), isBirth))
                binding.ivItemMenuPartyroomCandleFirst.visibility = View.GONE
                binding.ivItemMenuPartyroomCandleLast.visibility = View.GONE
            }
            else {
                binding.ivItemMenuPartyroomCandleFirst.visibility = View.VISIBLE
                binding.ivItemMenuPartyroomCandleLast.visibility = View.VISIBLE
                binding.ivItemMenuPartyroomCandleFirst.setImageResource(selectionCandle(birthString[0].toString().toInt(), isBirth))
                binding.ivItemMenuPartyroomCandleLast.setImageResource(selectionCandle(birthString[1].toString().toInt(), isBirth))
                binding.ivItemMenuPartyroomFireCandleFirst.visibility = View.GONE
                binding.ivItemMenuPartyroomCandleLast.visibility = View.GONE
            }
        }

        fun initListener(pos: Int) {
            binding.root.setOnClickListener {
                setPartyroomRv.onClick(pos)
            }
        }
    }

}
