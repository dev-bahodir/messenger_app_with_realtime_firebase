package dev.bahodir.realtimefirebaseentrance.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.bahodir.realtimefirebaseentrance.databinding.ItemFromBinding
import dev.bahodir.realtimefirebaseentrance.databinding.ItemToBinding
import dev.bahodir.realtimefirebaseentrance.models.Message
import java.util.ArrayList

class MessageAdapter(var currentUid: String, var list: ArrayList<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /*class DU : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.message == newItem.message
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }

    }*/

    inner class FromVH(var fromBinding: ItemFromBinding) : RecyclerView.ViewHolder(fromBinding.root) {
        fun onBind(message: Message) {
            fromBinding.message.text = message.message
            fromBinding.date.text = message.date
        }
    }

    inner class ToVH(var toBinding: ItemToBinding) : RecyclerView.ViewHolder(toBinding.root) {
        fun onBind(message: Message) {
            toBinding.message.text = message.message
            toBinding.date.text = message.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1)
            FromVH(ItemFromBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        else
            ToVH(ItemToBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FromVH)
            holder.onBind(list[position])
        else if (holder is ToVH)
            holder.onBind(list[position])
    }

    override fun getItemViewType(position: Int): Int {
        if (list[position].from == currentUid)
            return 1
        return 2
    }

    override fun getItemCount(): Int {
        return list.size
    }
}