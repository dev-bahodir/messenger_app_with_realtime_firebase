package dev.bahodir.realtimefirebaseentrance.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.bahodir.realtimefirebaseentrance.fragment.vpforfragment.ChatsFragment
import dev.bahodir.realtimefirebaseentrance.fragment.vpforfragment.GroupsFragment

class VPAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position ==0)
            ChatsFragment()
        else
            GroupsFragment()
    }
}