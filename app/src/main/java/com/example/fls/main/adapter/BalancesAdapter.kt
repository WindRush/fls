package com.example.fls.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.fls.R
import com.example.fls.bean.ConvertAssetItemData

class BalancesAdapter(val balances: List<ConvertAssetItemData>): RecyclerView.Adapter<BalanceViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BalanceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler_asset, parent, false)
        return BalanceViewHolder(view)
    }

    override fun getItemCount(): Int {
        return balances.size
    }

    override fun onBindViewHolder(holder: BalanceViewHolder, position: Int) {
        val balanceItem = balances[position]
        Glide.with(holder.itemView.context)
            .load(balanceItem.icon)
            .placeholder(R.mipmap.ic_launcher)
            .into(holder.ivCurrency)
        holder.tvCurrencyName.text = balanceItem.name
        holder.tvValue.text = (balanceItem.baseValue ?: "0").plus(" ${balanceItem.symbol}")
        holder.tvFormatValue.text = "$ ".plus(balanceItem.convertValue ?: "0")
    }


}

class BalanceViewHolder(view: View ): ViewHolder(view) {
    val ivCurrency = view.findViewById<ImageView>(R.id.ivCurrency)
    val tvCurrencyName = view.findViewById<TextView>(R.id.tvCurrencyName)
    val tvValue = view.findViewById<TextView>(R.id.tvValue)
    val tvFormatValue = view.findViewById<TextView>(R.id.tvFormatValue)

}