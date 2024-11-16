package com.example.tabs.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tabs.fragments.ConsultaFragment
import com.example.tabs.fragments.FormularioFragment
import com.example.tabs.fragments.PresentacionFragment

class TabPageAdapter(
    framentManager :FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter (framentManager , lifecycle)
{
    override fun getItemCount(): Int  = 3
    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> PresentacionFragment()
            1 -> FormularioFragment()
            2 -> ConsultaFragment()
            else -> PresentacionFragment()
        }
    }


}