package com.example.fls.scroll

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.fls.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator

class CollectionSetActivity: AppCompatActivity() {

    val tabs: TabLayout by lazy { findViewById(R.id.tabs) }



    val frag1 = CollectTabFragment()
    val frag2 = CollectTabFragment()

    val titles = arrayOf("自选1", "自选2")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collect)

        tabs.addTab(tabs.newTab().apply {
            text = titles[0]
        })
        tabs.addTab(tabs.newTab().apply {
            text = titles[1]
        })

        tabs.addOnTabSelectedListener(object : OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                change(tab?.position?: 0)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
        change(0)

    }

    private fun change(position: Int) {
        val fragment = if (position == 0) frag1 else frag2
        val hideFragment = if (position == 1) frag1 else frag2
        if (fragment == hideFragment) return
        val transaction = supportFragmentManager.beginTransaction()
        if (!fragment.isAdded) {
            transaction.add(R.id.frameLayout, fragment, fragment::class.simpleName)
        }
        transaction.show(fragment)
        if (hideFragment.isAdded) {
            transaction.hide(hideFragment)
        }
        transaction.commitAllowingStateLoss()

    }


}