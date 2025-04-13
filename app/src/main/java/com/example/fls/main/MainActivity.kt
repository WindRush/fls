package com.example.fls.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.fls.base.BaseActivity
import com.example.fls.main.model.MainViewModel
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fls.R
import com.example.fls.bean.ConvertAssetItemData
import com.example.fls.main.adapter.BalancesAdapter
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch

class MainActivity: BaseActivity<MainViewModel>() {

    override val vm: MainViewModel by viewModels()

    override val layout: Int = R.layout.activity_main

    private var tvTotal: TextView? = null
    private var rv: RecyclerView? = null
    private var mAdapter: BalancesAdapter? = null
    private var balances: ArrayList<ConvertAssetItemData> = arrayListOf()

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tvTotal = findViewById(R.id.tvTotal)
        rv = findViewById(R.id.rv)
        mAdapter = BalancesAdapter(balances)
        val layoutManager = LinearLayoutManager(this)
        rv?.layoutManager = layoutManager
        rv?.adapter = mAdapter
        val dividerItemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        rv?.addItemDecoration(dividerItemDecoration)

        vm.errorLiveData.observe(this) {
            Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
        }

        vm.balancesLiveData.observe(this@MainActivity) {
            if (it == null) {
                balances.clear()
                mAdapter?.notifyDataSetChanged()
            } else {
                it.assets?.let {
                    balances.clear()
                    balances.addAll(it)
                    mAdapter?.notifyDataSetChanged()
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.uiStateFlow.drop(1).collect { state ->
                    when(state) {
                        null -> {
                            tvTotal?.text = "0.00 USD"
                        }
                        else -> {
                            tvTotal?.text = "$state USD"
                        }
                    }
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        vm.requestCombineData()
    }
}