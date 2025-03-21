package com.mgado.expensetracker.models
import com.mgado.expensetracker.R

data class Wallet(val id: Int,
                  val name: String,
                  val image: Int,
                  val backgroundColor: Int,
                  val userBalance: Double)

val dummyWallets = listOf(
    Wallet(id = 1,
        name = "eSewa",
        image = R.drawable.e,
        backgroundColor = R.color.green,
        userBalance = 10000.rangeTo(90000).random().toDouble()),
    Wallet(id = 2,
        name = "Khalti",
        image = R.drawable.k,
        backgroundColor = R.color.purple_500,
        userBalance = 10000.rangeTo(90000).random().toDouble()),
    Wallet(id = 3,
        name = "HamroPay",
        image = R.drawable.send,
        backgroundColor = R.color.dark_red,
        userBalance = 10000.rangeTo(90000).random().toDouble())

)