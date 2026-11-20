package com.ctvgrk.app

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ctvgrk.app.databinding.ItemCameraBinding

class CameraAdapter : ListAdapter<CameraItem, CameraAdapter.VH>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<CameraItem>() {
            override fun areItemsTheSame(old: CameraItem, new: CameraItem) =
                old.serial == new.serial && old.channel == new.channel

            override fun areContentsTheSame(old: CameraItem, new: CameraItem) = old == new
        }
    }

    inner class VH(private val binding: ItemCameraBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CameraItem) {
            binding.tvName.text = item.name
            binding.tvSerial.text = "${item.serial}  •  ch${item.channel}"
            binding.tvStatus.text = item.status
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemCameraBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }
}
