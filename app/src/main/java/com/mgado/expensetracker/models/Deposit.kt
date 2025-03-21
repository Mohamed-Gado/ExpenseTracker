package com.mgado.expensetracker.models

data class Deposit(val id: Int,
                   val label: String = "Deposit on Nabil Bank Saving Acc.",
                   val date: String = "2020/12/12",
                   val price: Double,
                   val depositId: String)

val dummyDeposits = 1.rangeTo(70).map {
    val id = 1.rangeTo(70).random()
    Deposit(id = it,
        depositId = "NPI000$id",
        price = (71 - id) * 15.0)
}