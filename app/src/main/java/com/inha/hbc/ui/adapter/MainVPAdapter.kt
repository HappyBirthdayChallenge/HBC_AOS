package com.inha.hbc.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.inha.hbc.R
import com.inha.hbc.databinding.ItemMainPageBinding
import com.inha.hbc.ui.assist.dpToPx
import com.inha.hbc.ui.assist.selectionCandle
import com.inha.hbc.util.fragmentmanager.MainFragmentManager
import com.inha.hbc.util.sharedpreference.GlobalApplication
import java.util.Calendar

class MainVPAdapter(var pageData: ArrayList<Int>): RecyclerView.Adapter<MainVPAdapter.PageHolder>() {

    lateinit var binding: ItemMainPageBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageHolder {
        binding = ItemMainPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PageHolder(binding, pageData)
    }

    override fun onBindViewHolder(holder: PageHolder, position: Int) {
        holder.init(position)
    }

    override fun getItemCount(): Int = pageData.size

    class PageHolder(val binding: ItemMainPageBinding, val pageData: ArrayList<Int>): RecyclerView.ViewHolder(binding.root) {

        val bgOptions = RequestOptions().fitCenter()
            .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)

        fun init(pos: Int) {
            initBg(pos)
            initCake(pos)
            initObjectPos()
            initObject(pos)
        }
        fun initBg(pos: Int) {
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

        fun initCake(pos: Int) {
            if (pos == 1) {
                binding.ivItemMainCake.setImageResource(MainFragmentManager.cakeId)
                val date = MainFragmentManager.personInfo.data!!.birth_date.date
                val month = MainFragmentManager.personInfo.data!!.birth_date.month

                val cal = Calendar.getInstance()
                val mon = cal.get(Calendar.MONTH) + 1
                val day = cal.get(Calendar.DATE)

                var isBirth = false
                if (mon == month && date== day) {
                    isBirth = true
                }

                val birth =  cal.get(Calendar.YEAR) - MainFragmentManager.personInfo.data!!.birth_date.year + 1
                var birthString = ""
                if (birth < 10) {
                    birthString = "0$birth"
                }
                else {
                    birthString = "$birth"
                }
                binding.ivItemMainCandleFirst.setImageResource(selectionCandle(birthString[0].toString().toInt(), isBirth))
                binding.ivItemMainCandleLast.setImageResource(selectionCandle(birthString[1].toString().toInt(), isBirth))

                binding.clItemMainBackLine.visibility = View.GONE
                binding.clItemMainFrontLine.visibility = View.GONE
                binding.clItemMainFoodBack.visibility = View.GONE
                binding.clItemMainFoodFront.visibility = View.GONE
            }
            else {
                binding.ivItemMainCake.visibility = View.GONE
                binding.ivItemMainCandleLast.visibility = View.GONE
                binding.ivItemMainCandleFirst.visibility = View.GONE
            }
        }

        fun initObject(pos:Int) {

        }

        fun initObjectPos() {
            binding.ivItemMainBackObj1.setPadding(dpToPx(genRand()),dpToPx(genRand())/2,dpToPx(genRand()), dpToPx(genRand()))
            binding.ivItemMainBackObj2.setPadding(dpToPx(genRand()),dpToPx(genRand())/2,dpToPx(genRand()), dpToPx(genRand()))
            binding.ivItemMainBackObj3.setPadding(dpToPx(genRand()),dpToPx(genRand())/2,dpToPx(genRand()), dpToPx(genRand()))
            binding.ivItemMainBackObj4.setPadding(dpToPx(genRand()),dpToPx(genRand())/2,dpToPx(genRand()), dpToPx(genRand()))
            binding.ivItemMainFrontObj1.setPadding(dpToPx(genRand()),dpToPx(genRand()),dpToPx(genRand()), dpToPx(genRand())/2)
            binding.ivItemMainFrontObj2.setPadding(dpToPx(genRand()),dpToPx(genRand()),dpToPx(genRand()), dpToPx(genRand())/2)
            binding.ivItemMainFrontObj3.setPadding(dpToPx(genRand()),dpToPx(genRand()),dpToPx(genRand()), dpToPx(genRand())/2)
            binding.ivItemMainFrontObj4.setPadding(dpToPx(genRand()),dpToPx(genRand()),dpToPx(genRand()), dpToPx(genRand())/2)
        }

        fun genRand(): Int {
            return (0..10).random()
        }

    }
}
