package com.example.dollarinfo.service

import com.example.dollarinfo.data.DollarResponse
import com.example.dollarinfo.data.DollarURUResponse
import com.example.dollarinfo.data.DollarUru
import java.util.Collections

class DollarService{

    companion object{
        var dollarEURValue : DollarResponse? = null
        var listDollarsARG : List<DollarResponse> = Collections.emptyList()
        var listDollarURU : Map<String,DollarUru> = Collections.emptyMap()

        @JvmStatic
        fun findDollarBlueSale(): String {
            val blue = listDollarsARG.find { it.moneyName == "Blue" }
            return blue!!.saleValue
        }

        fun finDollarBlueSaleDateUpdate(): String {
            val blue = listDollarsARG.find { it.moneyName == "Blue" }
            return blue!!.updateDate
        }
    }

}