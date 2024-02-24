package com.example.dollarinfo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dollarinfo.adapters.DolarUruAdapter
import com.example.dollarinfo.databinding.ActivityDollarUruactivityBinding
import com.example.dollarinfo.service.DollarService

class DollarURUActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDollarUruactivityBinding
    private lateinit var dollarUruAdapter : DolarUruAdapter
    private val linearLayoutManager = LinearLayoutManager(this)
    private lateinit var date : String
    private lateinit var time : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDollarUruactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        date = intent.getStringExtra("DATE").toString()
        time = intent.getStringExtra("TIME").toString()
        supportActionBar?.title = "Dolares URU, Fecha: $date"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        dollarUruAdapter = DolarUruAdapter(DollarService.listDollarURU,date,time)
        binding.rvDollarUru.apply {
            adapter = dollarUruAdapter
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = linearLayoutManager
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}