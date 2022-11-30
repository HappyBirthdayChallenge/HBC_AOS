package com.inha.hbc.ui.adapter

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inha.hbc.R
import com.inha.hbc.databinding.ItemLetterReadBinding
import com.inha.hbc.util.fragmentmanager.MainFragmentManager


class LetterReadRVAdapter(val mediaArr: List<String>): RecyclerView.Adapter<LetterReadRVAdapter.MediaHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaHolder {
        val binding = ItemLetterReadBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MediaHolder(binding, mediaArr)
    }

    override fun onBindViewHolder(holder: MediaHolder, position: Int) {
        holder.init(position)
    }

    override fun getItemCount(): Int = mediaArr.size

    class MediaHolder(val binding :ItemLetterReadBinding, val mediaArr: List<String>): RecyclerView.ViewHolder(binding.root) {
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

                binding.ivItemLetterRead.setImageBitmap(bitmap)
                binding.ivItemLetterReadIcon.setImageResource(R.drawable.ic_letter_record_play)
            } else if (fileType == "jpg" || fileType == "png" || fileType == "peg" || fileType == "gif") {//사진
                Glide.with(MainFragmentManager.baseActivity.applicationContext).load(mediaArr[pos])
                    .into(binding.ivItemLetterRead)
            } else {//음
                binding.ivItemLetterReadIcon.setImageResource(R.drawable.ic_letter_menu_record)
            }

            initListener()
        }

        fun initListener() {

        }
    }
}