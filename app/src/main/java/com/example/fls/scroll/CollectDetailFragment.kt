package com.example.fls.scroll

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fls.R

class CollectDetailFragment: Fragment() {

    private var rootView: View? = null

    private var collectRecyclerView: CollectRecyclerView? = null

    private var collectList = arrayListOf(
        "东方财富",
        "中国茅台",
        "工商银行",
        "小米集团",
        "阿里巴巴",
        "腾讯",
        "科大讯飞",
        "中国南车",
        "沪深300",
        "中芯国际",
        "海康威视",
        "东方财富",
        "中国茅台",
        "工商银行",
        "小米集团",
        "阿里巴巴",
        "腾讯",
        "科大讯飞",
        "中国南车",
        "沪深300",
        "中芯国际",
        "海康威视"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_collect_detail, container, false)
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectRecyclerView = rootView as CollectRecyclerView?

        collectRecyclerView?.bindData(collectList)
    }



}