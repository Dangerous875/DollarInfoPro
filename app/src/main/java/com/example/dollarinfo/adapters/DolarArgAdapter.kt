package com.example.dollarinfo.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.dollarinfo.data.DollarResponse
import com.example.dollarinfo.databinding.ItemDollarLayoutBinding

class DolarArgAdapter(private val listDollars : List<DollarResponse>, private val currentDate: String , private val time :String):RecyclerView.Adapter<DolarArgAdapter.DolarArgViewHolder>() {
    class DolarArgViewHolder (val binding : ItemDollarLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DolarArgViewHolder {
        val binding = ItemDollarLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DolarArgViewHolder(binding)
    }

    override fun getItemCount(): Int = listDollars.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DolarArgViewHolder, position: Int) {
        val item = listDollars[position]
        val bind = holder.binding
        val context = bind.root.context

        bind.moneyName.text = " Dólar ${item.moneyName}"
        bind.compra.text = " Compra: $${item.purchaseValue}"
        bind.venta.text = " Venta: $${item.saleValue}"
        bind.date.text = "Last Update: $currentDate, $time hs"

        bind.card.setOnClickListener {
            Toast.makeText(context," Dólar ${item.moneyName}",Toast.LENGTH_SHORT).show()
        }
    }
}