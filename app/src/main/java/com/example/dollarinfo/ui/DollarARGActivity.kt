package com.example.dollarinfo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dollarinfo.adapters.DolarArgAdapter
import com.example.dollarinfo.databinding.ActivityDollarArgactivityBinding
import com.example.dollarinfo.service.DollarService

class DollarARGActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDollarArgactivityBinding
    private val linearLayoutManager = LinearLayoutManager(this)
    private lateinit var dolarARGadapter : DolarArgAdapter
    private lateinit var date : String
    private lateinit var time : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDollarArgactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        date = intent.getStringExtra("DATE").toString()
        time = intent.getStringExtra("TIME").toString()
        supportActionBar?.title = "Dolares ARG, Fecha: $date"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initRecyclerView()
    }

    private fun initRecyclerView() {

        dolarARGadapter = DolarArgAdapter(DollarService.listDollarsARG,date,time)

        binding.rvDollarArg.apply {
            adapter = dolarARGadapter
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = linearLayoutManager
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}