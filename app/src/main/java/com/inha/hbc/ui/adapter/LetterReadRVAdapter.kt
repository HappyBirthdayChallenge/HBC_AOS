package com.inha.hbc.ui.adapter

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inha.hbc.R
import com.inha.hbc.databinding.ItemLetterBinding
import com.inha.hbc.databinding.ItemLetterObjectBinding
import com.inha.hbc.databinding.ItemLetterReadBinding
import com.inha.hbc.util.fragmentmanager.MainFragmentManager


class LetterReadRVAdapter(val mediaArr: List<String>): RecyclerView.Adapter<LetterReadRVAdapter.MediaHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaHolder {
        val binding = ItemLetterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MediaHolder(binding, mediaArr)
    }

    override fun onBindViewHolder(holder: MediaHolder, position: Int) {
        holder.init(position)
    }

    override fun getItemCount(): Int = mediaArr.size

    class MediaHolder(val binding :ItemLetterBinding, val mediaArr: List<String>): RecyclerView.ViewHolder(binding.root) {
        fun init(pos: Int) {
            val fileType = mediaArr[pos].substring(mediaArr[pos].length - 3, mediaArr[pos].length)
            if (fileType == "mp4") {//동영상
                var bitmap: Bitmap? = null
                var mediaMetadataRetriever = MediaMetadataRetriever()
                if (Build.VERSION.SDK_INT >= 14) mediaMetadataRetriever.setDataSource(
                    mediaArr[pos],
                    HashMap()
                )
                else mediaMetadataRetriever.setDataSource(mediaArr[pos])
                //   mediaMetadataRetriever.setDataSource(videoPath);
                bitmap =
                    mediaMetadataRetriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST)
                mediaMetadataRetriever?.release()

                binding.ivItemLetter.setImageBitmap(bitmap)
                binding.ivItemLetterAttach.setImageResource(R.drawable.ic_letter_record_play)
                binding.ivItemLetterAttach.visibility = View.VISIBLE
            } else if (fileType == "jpg" || fileType == "png" || fileType == "peg" || fileType == "gif") {//사진
                Glide.with(MainFragmentManager.baseActivity.applicationContext).load(mediaArr[pos])
                    .into(binding.ivItemLetter)
            } else {//음성
                binding.ivItemLetterAttach.setImageResource(R.drawable.ic_letter_menu_record)
                binding.ivItemLetterAttach.visibility = View.VISIBLE
            }

            initListener()
        }

        fun initListener() {
            binding.root.setOnClickListener {

            }
        }
    }
}