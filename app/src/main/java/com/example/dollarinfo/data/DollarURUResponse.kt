package com.example.dollarinfo.data

import com.google.gson.annotations.SerializedName

data class DollarURUResponse(
    @SerializedName("dateISOString") val dateUpdate: String,
    @SerializedName("rates") val listDollarUruResponse: Map<String,DollarUru>
)

