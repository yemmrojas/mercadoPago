package com.mercadolibre.mercadopago.util

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

fun toPrice(text: String?): String {
    if (text.isNullOrEmpty()) return "$ 0"
    val sym = DecimalFormatSymbols.getInstance()
    sym.groupingSeparator = '.'
    return try {
        DecimalFormat("$ ###,###", sym).format(text.replace(",", ".").toFloat())
    } catch (e: Exception) {
        ""
    }
}