package com.example.fls.scroll

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.fls.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CollectTabFragment: Fragment() {


    val fragments by lazy {
        arrayListOf(
            CollectDetailFragment(),
            CollectDetailFragment()
        )
    }

    val titles = arrayOf("港股", "美股")

    private var rootView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_collect, container, false)
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabs = view.findViewById<TabLayout>(R.id.tabs)
        val pagers = view.findViewById<ViewPager2>(R.id.pagers)
        pagers.adapter = object : FragmentStateAdapter(childFragmentManager, lifecycle) {
            override fun getItemCount(): Int = fragments.count()

            override fun createFragment(position: Int): Fragment {
                return fragments[position]
            }
        }

        TabLayoutMediator(tabs, pagers) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

}