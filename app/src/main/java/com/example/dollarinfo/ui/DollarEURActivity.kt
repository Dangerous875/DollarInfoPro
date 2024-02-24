package com.example.dollarinfo.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dollarinfo.databinding.ActivityDollarEuractivityBinding
import com.example.dollarinfo.service.DollarService

class DollarEURActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDollarEuractivityBinding
    private lateinit var date : String
    private lateinit var time : String
    private val dollarEUR = DollarService.dollarEURValue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDollarEuractivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        date = intent.getStringExtra("DATE").toString()
        time = intent.getStringExtra("TIME").toString()
        supportActionBar?.title = "Cambio EUR, Fecha: $date"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        chargeItems()
    }

    @SuppressLint("SetTextI18n")
    private fun chargeItems() {
        binding.date.text = "Last Update: $date, $time hs"
        binding.moneyName.text = "${dollarEUR!!.moneyName} to ARG"
        binding.compra.text = "Compra: $${dollarEUR.purchaseValue}"
        binding.venta.text = "Venta : $${dollarEUR.saleValue}"
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}