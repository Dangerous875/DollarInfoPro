package com.example.dollarinfo.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.dollarinfo.data.DollarUru
import com.example.dollarinfo.databinding.ItemDollarLayoutBinding

class DolarUruAdapter(private val mapDollarUru: Map<String, DollarUru>, private val currentDate: String , private val time :String) :
    RecyclerView.Adapter<DolarUruAdapter.DolarUruViewHolder>() {

    private val listKeysMap : List<String> = mapDollarUru.keys.toList()

    class DolarUruViewHolder (val binding: ItemDollarLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DolarUruViewHolder {
        val binding = ItemDollarLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DolarUruViewHolder(binding)
    }

    override fun getItemCount(): Int = mapDollarUru.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DolarUruViewHolder, position: Int) {
        val itemKey = listKeysMap[position]
        val valueKey = mapDollarUru[itemKey]
        val bind = holder.binding
        val context = bind.root.context

        bind.moneyName.text = itemKey
        bind.date.text = "Last Update: $currentDate, $time hs"
        bind.compra.text = "Compra: $${valueKey!!.buy}"
        bind.venta.text = "Venta : $${valueKey.sell}"

        bind.card.setOnClickListener {
            Toast.makeText(context, itemKey,Toast.LENGTH_SHORT).show()
        }
    }
}