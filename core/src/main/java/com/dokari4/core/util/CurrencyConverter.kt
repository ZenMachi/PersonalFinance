package com.dokari4.core.util

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

object CurrencyConverter {

    fun convertToRupiah(amount: BigDecimal): String {
        val format = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        return format.format(amount).replace("Rp", "Rp. ")
    }
}