package com.mgado.expensetracker.models

data class BudgetPlan(val id: Int,
                      val name: String = "Dummy Plan",
                      val date: String = "2021/04/12",
                      val total: Double)

val dummyPlans = 1.rangeTo(3).map {
    BudgetPlan(id = it, total = 10000.rangeTo(90000).random().toDouble())
}