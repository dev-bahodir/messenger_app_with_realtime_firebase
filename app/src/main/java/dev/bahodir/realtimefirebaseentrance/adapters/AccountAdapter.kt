package dev.bahodir.realtimefirebaseentrance.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.bahodir.realtimefirebaseentrance.databinding.ItemAccountBinding
import dev.bahodir.realtimefirebaseentrance.models.Account
import java.util.ArrayList

class AccountAdapter(var listener: OnItemClickListener) : ListAdapter<Account, AccountAdapter.VH>(DU()) {

    class DU : DiffUtil.ItemCallback<Account>() {
        override fun areItemsTheSame(oldItem: Account, newItem: Account): Boolean {
            return oldItem.uid == newItem.uid
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Account, newItem: Account): Boolean {
            return oldItem == newItem
        }
    }

    interface OnItemClickListener {
        fun onItemClick(account: Account, position: Int, view: View)
    }

    inner class VH(var binding: ItemAccountBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(account: Account, position: Int) {

            Picasso.get().load(account.photoUrl).into(binding.image)
            binding.name.text = account.displayName

            itemView.setOnClickListener {
                listener.onItemClick(account, position, it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemAccountBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(getItem(position), position)
    }
}