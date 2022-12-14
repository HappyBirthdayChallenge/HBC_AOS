package com.inha.hbc.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.inha.hbc.R
import com.inha.hbc.data.remote.resp.message.SearchDecoSuccess
import com.inha.hbc.databinding.ItemMainPageBinding
import com.inha.hbc.ui.assist.dpToPx
import com.inha.hbc.ui.assist.selectionCandle
import com.inha.hbc.ui.assist.serverDecoToId
import com.inha.hbc.ui.main.view.SearchDecoView
import com.inha.hbc.util.fragmentmanager.MainFragmentManager
import com.inha.hbc.util.network.room.RoomRetrofitService
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

    class PageHolder(val binding: ItemMainPageBinding, val pageData: ArrayList<Int>): RecyclerView.ViewHolder(binding.root), SearchDecoView {



        val bgOptions = RequestOptions().fitCenter()
            .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)

        var pos = 0


        fun init(pos: Int) {
            this.pos = pos

            initBg(pos)
            initCake(pos)

            if (pageData[pos] == 2) {
                binding.lavPageLoading.visibility = View.VISIBLE
                RoomRetrofitService().searchDeco(
                    (pos - 1).toString(),
                    MainFragmentManager.roomId.toString(),
                    this
                )
            }
            else if (pageData[pos] == 1) {
                hideView(false)
            }
            else {
                hideView(true)
            }
        }

        override fun onSearchDecoSuccess(resp: SearchDecoSuccess) {
            binding.ivItemMainCake.visibility = View.GONE
            binding.ivItemMainCandleLast.visibility = View.GONE
            binding.ivItemMainCandleFirst.visibility = View.GONE

            initObjectPos()
            initObject(pos, resp)
            binding.lavPageLoading.visibility = View.GONE
        }

        fun initBg(pos: Int) {
            if (pageData[pos] == 0) {
                Glide.with(itemView).load(R.drawable.bg_type1_left).apply(bgOptions).into(binding.ivItemMainBg)
            }
            else if (pageData[pos] == 1) {
                Glide.with(itemView).load(R.drawable.bg_type1_front).apply(bgOptions).into(binding.ivItemMainBg)
            }
            else if (pageData[pos] == 2) {
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
                if (isBirth) {
                    binding.ivItemMainFireCandleFirst.visibility = View.VISIBLE
                    binding.ivItemMainFireCandleLast.visibility = View.VISIBLE
                    binding.ivItemMainFireCandleFirst.setImageResource(selectionCandle(birthString[0].toString().toInt(), isBirth))
                    binding.ivItemMainFireCandleLast.setImageResource(selectionCandle(birthString[1].toString().toInt(), isBirth))
                    binding.ivItemMainCandleFirst.visibility = View.GONE
                    binding.ivItemMainCandleLast.visibility = View.GONE
                }
                else {
                    binding.ivItemMainCandleFirst.visibility = View.VISIBLE
                    binding.ivItemMainCandleLast.visibility = View.VISIBLE
                    binding.ivItemMainCandleFirst.setImageResource(selectionCandle(birthString[0].toString().toInt(), isBirth))
                    binding.ivItemMainCandleLast.setImageResource(selectionCandle(birthString[1].toString().toInt(), isBirth))
                    binding.ivItemMainFireCandleFirst.visibility = View.GONE
                    binding.ivItemMainFireCandleLast.visibility = View.GONE
                }

                binding.clItemMainBackLine.visibility = View.GONE
                binding.clItemMainFrontLine.visibility = View.GONE
                binding.clItemMainFoodBack.visibility = View.GONE
                binding.clItemMainFoodFront.visibility = View.GONE
            }
        }

        fun initObject(pos:Int, resp: SearchDecoSuccess) {
            binding.clItemMainFoodBack.visibility = View.VISIBLE
            binding.clItemMainFoodFront.visibility = View.VISIBLE
            binding.clItemMainBackLine.visibility = View.VISIBLE
            binding.clItemMainFrontLine.visibility = View.VISIBLE
            val dollArr = arrayListOf(binding.ivItemMainBackObj1, binding.ivItemMainFrontObj2, binding.ivItemMainFrontObj3, binding.ivItemMainBackObj4)
            val giftArr = arrayListOf(binding.ivItemMainFrontObj1, binding.ivItemMainBackObj2, binding.ivItemMainBackObj3, binding.ivItemMainFrontObj4)
            val foodArr = arrayListOf(binding.ivItemMainFoodFrontObj1, binding.ivItemMainFoodBackObj2, binding.ivItemMainFoodBackObj3, binding.ivItemMainFoodFrontObj4)
            val drinkArr = arrayListOf(binding.ivItemMainFoodBackObj1, binding.ivItemMainFoodFrontObj2, binding.ivItemMainFoodFrontObj3, binding.ivItemMainFoodBackObj4)

            for (i in 0 until resp.data!!.dolls.size) {
                dollArr[i].visibility = View.VISIBLE
                dollArr[i].setImageResource(serverDecoToId(resp.data!!.dolls[i].decoration_type))
                dollArr[i].setOnClickListener {
                    MainFragmentManager.openLetter(resp.data!!.dolls[i].message_id)
                }
            }
            for (i in resp.data!!.dolls.size until 4) {
                dollArr[i].visibility = View.INVISIBLE
            }
            for (i in 0 until resp.data!!.gifts.size) {
                giftArr[i].visibility = View.VISIBLE
                giftArr[i].setImageResource(serverDecoToId(resp.data!!.gifts[i].decoration_type))
                giftArr[i].setOnClickListener {
                    MainFragmentManager.openLetter(resp.data!!.gifts[i].message_id)
                }
            }
            for (i in resp.data!!.gifts.size until 4) {
                giftArr[i].visibility = View.INVISIBLE
            }
            for (i in 0 until resp.data!!.foods.size) {
                foodArr[i].visibility = View.VISIBLE
                foodArr[i].setImageResource(serverDecoToId(resp.data!!.foods[i].decoration_type))
                foodArr[i].setOnClickListener {
                    MainFragmentManager.openLetter(resp.data!!.foods[i].message_id)
                }
            }
            for (i in resp.data!!.foods.size until 4) {
                foodArr[i].visibility = View.INVISIBLE
            }
            for (i in 0 until resp.data!!.drinks.size) {
                drinkArr[i].visibility = View.VISIBLE
                drinkArr[i].setImageResource(serverDecoToId(resp.data!!.drinks[i].decoration_type))
                drinkArr[i].setOnClickListener {
                    MainFragmentManager.openLetter(resp.data!!.drinks[i].message_id)
                }
            }
            for (i in resp.data!!.drinks.size until 4) {
                drinkArr[i].visibility = View.INVISIBLE
            }
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

        fun hideView(all: Boolean) {
            if (all) {
                binding.ivItemMainCake.visibility = View.GONE
                binding.ivItemMainCandleLast.visibility = View.GONE
                binding.ivItemMainCandleFirst.visibility = View.GONE//????????? ?????? ??????
            }


            binding.clItemMainBackLine.visibility = View.GONE
            binding.clItemMainFrontLine.visibility = View.GONE
            binding.clItemMainFoodBack.visibility = View.GONE
            binding.clItemMainFoodFront.visibility = View.GONE//?????? ?????? ??????

        }

        fun genRand(): Int {
            return (0..10).random()
        }


        override fun onSearchDecoFailure() {
            TODO("Not yet implemented")
        }

    }
}
