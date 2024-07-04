package com.example.alne.view.Fridge
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.alne.view.Fridge.FridgeAllFragment
import com.example.alne.view.Fridge.FridgeColdFragment
import com.example.alne.view.Fridge.FridgeFreezeFragment

class FridgeVPAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> FridgeAllFragment()
            1-> FridgeColdFragment()
            else -> FridgeFreezeFragment()
        }
    }
}