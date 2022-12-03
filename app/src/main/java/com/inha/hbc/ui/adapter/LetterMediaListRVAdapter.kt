package com.inha.hbc.ui.adapter

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inha.hbc.R
import com.inha.hbc.databinding.ItemLetterBinding
import com.inha.hbc.util.fragmentmanager.LetterFragmentManager
import com.inha.hbc.util.fragmentmanager.MainFragmentManager


class LetterMediaListRVAdapter(): RecyclerView.Adapter<LetterMediaListRVAdapter.LetterHolder>() {
    lateinit var binding: ItemLetterBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LetterHolder {
        binding = ItemLetterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LetterHolder(binding)
    }

    override fun onBindViewHolder(holder: LetterHolder, position: Int) {
        holder.init(position)
    }

    override fun getItemCount(): Int = LetterFragmentManager.fileInfo.size + 1

    class LetterHolder(val binding: ItemLetterBinding): RecyclerView.ViewHolder(binding.root) {
        fun init(pos: Int) {
            initListener(pos)
            if(pos == 0) {
                binding.lavItemLetter.visibility = View.GONE
                binding.ivItemLetterDel.visibility = View.GONE
                binding.ivItemLetter.visibility = View.GONE
                binding.tvItemLetterTitle.visibility = View.VISIBLE
                binding.ivItemLetterAttach.visibility = View.VISIBLE
            }
            else {
                binding.tvItemLetterTitle.visibility = View.GONE
                binding.ivItemLetterAttach.visibility = View.GONE
                if (!LetterFragmentManager.fileInfo[pos - 1].success) {
                    binding.lavItemLetter.setAnimation("letter_file_loading.json")
                    binding.lavItemLetter.visibility = View.VISIBLE
                }
                else {
                    binding.lavItemLetter.visibility = View.GONE
                    binding.ivItemLetterDel.visibility = View.VISIBLE
                }
                val uri = LetterFragmentManager.fileInfo[pos - 1].uri
                when(LetterFragmentManager.fileInfo[pos - 1].type) { // 0 사진 1 동영상 2 음성
                    0 -> {
                        binding.ivItemLetter.setImageURI(uri)
                    }
                    1 -> {

                        binding.ivItemLetter.setImageBitmap(setThumbnail(LetterFragmentManager.fileInfo[pos - 1].path))
                        binding.ivItemLetterAttach.setImageResource(R.drawable.ic_letter_record_play)
                        binding.ivItemLetterAttach.visibility = View.VISIBLE
                    }
                    else -> {
                        binding.ivItemLetterAttach.setImageResource(R.drawable.ic_letter_menu_record)
                        binding.ivItemLetterAttach.visibility = View.VISIBLE
                    }
                }
            }
        }

        fun initListener(pos: Int) {
            binding.cvItemLetter.setOnClickListener {
                when (pos) {
                    0 -> {
                        LetterFragmentManager.letterBaseFragment.openList()
                    }
                    else -> {
                        LetterFragmentManager.openShow(pos)
                    }
                }
            }

            binding.ivItemLetterDel.setOnClickListener {
                LetterFragmentManager.fileInfo.removeAt(pos - 1)

                LetterFragmentManager.mediaAdapter.notifyDataSetChanged()
            }
        }

        fun setThumbnail(path: String): Bitmap? {
            val retriever = MediaMetadataRetriever()
            retriever.setDataSource(path)
            return retriever.getFrameAtTime((1 * 1000000).toLong(), MediaMetadataRetriever.OPTION_CLOSEST)
        }
    }
}