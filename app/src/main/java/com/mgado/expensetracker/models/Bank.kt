package com.mgado.expensetracker.models

data class Bank(val id: Int,
                val name: String = "Dummy Bank",
                val shortName: String = "DB",
                val userBalance: Double)

val dummyBanks = 1.rangeTo(9).map {
    val id = 1.rangeTo(9).random()
    Bank(id = it, userBalance = id * 10000.00)
}