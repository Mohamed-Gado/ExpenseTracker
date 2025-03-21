package com.mgado.expensetracker.models

data class Transaction(val id: Int,
                       val title: String = "Eat mo:mo near College restaurant \uD83E\uDD5F",
                       val date: String = "2020/12/12",
                       val category: TransactionCategory,
                       val price: Double = 130.0)

val dummyTransactions = 1.rangeTo(70).map {
    val id = 1.rangeTo(70).random()
    Transaction(id = it,
        category = dummyCategories.random(),
        price = (71 - id) * 15.0)
}
