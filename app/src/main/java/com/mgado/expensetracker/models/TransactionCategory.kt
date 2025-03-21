package com.mgado.expensetracker.models

import com.mgado.expensetracker.R

data class TransactionCategory(val title: String, val color: Int, val icon: Int)

val dummyTransactionTypes = listOf(
    "Income",
    "Expense",
    "Bill Sharing",
    "P2P Payment",
    "P2B Payment")

val dummyCategories = listOf(
    TransactionCategory(title = "Food", color = R.color.purple_700, icon = R.drawable.local_dining),
    TransactionCategory(title = "Education", color = R.color.blue, icon = R.drawable.auto_stories),
    TransactionCategory(title = "Repair", color = R.color.orange, icon = R.drawable.construction),
    TransactionCategory(title = "Cigarettes", color = R.color.green, icon = R.drawable.smoking_rooms))