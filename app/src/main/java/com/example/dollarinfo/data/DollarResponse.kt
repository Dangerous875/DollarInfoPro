package com.example.dollarinfo.data

import com.google.gson.annotations.SerializedName

data class DollarResponse(
    @SerializedName("nombre") val moneyName: String,
    @SerializedName("compra") val purchaseValue: String,
    @SerializedName("venta") val saleValue: String,
    @SerializedName("fechaActualizacion") val updateDate: String
)