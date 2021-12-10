package dev.bahodir.realtimefirebaseentrance.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dev.bahodir.realtimefirebaseentrance.databinding.ItemForGroupsBinding
import dev.bahodir.realtimefirebaseentrance.models.Group
import dev.bahodir.realtimefirebaseentrance.models.MessageGroup

class GroupsAdapter(
    var list: java.util.ArrayList<Group>,
    var onItemClickLitener: OnItemCklickListener,
) :
    RecyclerView.Adapter<GroupsAdapter.Vh>() {

    inner class Vh(var itemGroupBinding: ItemForGroupsBinding) :
        RecyclerView.ViewHolder(itemGroupBinding.root) {
        var firebaseDatabase = FirebaseDatabase.getInstance()
        var referense = firebaseDatabase.getReference("group_messages")

        fun onBind(group: Group, position: Int) {
            var count = 0
            itemGroupBinding.nameUser.text = group.groupName
            itemView.setOnClickListener {
                onItemClickLitener.onItemClick(group, position)
            }
            referense.child("${list[position].key}")
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {

                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        var listMessagesGroup = ArrayList<MessageGroup>()
                        val children = snapshot.children
                        for (child in children) {
                            if (child != null) {
                                listMessagesGroup.add(child.getValue(MessageGroup::class.java)!!)
                            }
                        }

                    }
                })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemForGroupsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    interface OnItemCklickListener {
        fun onItemClick(group: Group, position: Int)
    }

}