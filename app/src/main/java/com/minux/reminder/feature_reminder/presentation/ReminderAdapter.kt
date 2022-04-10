package com.minux.reminder.feature_reminder.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.minux.reminder.R
import com.minux.reminder.databinding.ItemReminderBinding
import com.minux.reminder.feature_reminder.domain.model.Reminder

class ReminderAdapter(
    private val context: Context,
): ListAdapter<Reminder, ReminderAdapter.ViewHolder>(ReminderDiffCallback) {
    private lateinit var binding: ItemReminderBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemReminderBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemReminderBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(reminder: Reminder) {
            Glide.with(context)
                .load( if(reminder.isActivated) R.drawable.ic_checked else R.drawable.ic_not_checked )
                .into(binding.activeIv)

            binding.timeTv.text = reminder.time
            binding.nameTv.text = reminder.name
        }
    }

    object ReminderDiffCallback : DiffUtil.ItemCallback<Reminder>() {
        override fun areItemsTheSame(oldItem: Reminder, newItem: Reminder): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Reminder, newItem: Reminder): Boolean {
            return oldItem.id == newItem.id
        }
    }
}