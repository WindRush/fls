package com.example.fls.scroll

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fls.R

class CollectDetailAdapter(var detailPaddingStart: Int = 0, val collectData: List<String>,): RecyclerView.Adapter<CollectDetailViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectDetailViewHolder {
        return CollectDetailViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_collect_detail, parent, false))
    }

    override fun getItemCount() = collectData.size

    override fun onBindViewHolder(holder: CollectDetailViewHolder, position: Int) {
        holder.tvName.text = collectData[position]
        holder.llDetail.setPadding(detailPaddingStart, 0,0,0)
    }

}

class CollectDetailViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val tvName = view.findViewById<TextView>(R.id.tvName)
    val llDetail = view.findViewById<LinearLayout>(R.id.llDetail)
}
