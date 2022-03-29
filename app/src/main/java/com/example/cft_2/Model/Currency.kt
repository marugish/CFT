package com.example.cft_2.Model

import org.json.JSONArray
import java.util.*

class CentralBank(
    val date: String,
    val previousDate: String,
    val previousURL: String,
    val timestamp: String,
    val infovaluteslist: ArrayList<Currency>
)

class Currency(
    val id: String,
    val numCode: String,
    val CharCode: String,
    val nominal: Int,
    val name: String,
    val value: Double,
    val previous: Double
)
