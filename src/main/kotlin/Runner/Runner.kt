package com.lukaorocha.Runner

import com.lukaorocha.config.Config
import com.lukaorocha.providers.NotionProvider
import com.lukaorocha.providers.notionProvider

class Runner {
    fun run(config: Config) {
        notionProvider(config) {
            val balances = fetchMonthlyBalances()
            val expenses = fetchExpenses()
            val incomes = fetchIncomes()

            for (balance in balances) {
                val expensesInMonth = expenses.filter {
                    it.properties.date.date.start.after(balance.properties.startDate.date.start) &&
                    it.properties.date.date.start.before(balance.properties.endDate.date.start)
                }

                val incomesInMonth = incomes.filter {
                    it.properties.date.date.start.after(balance.properties.startDate.date.start) &&
                    it.properties.date.date.start.before(balance.properties.endDate.date.start)
                }

                println("Expenses in ${balance.properties.endDate.date.start}")
                println(expensesInMonth.map { it.properties.name.title.fold("") { names, expense -> "$names\n${expense.plainText}" } })

                println("Incomes in ${balance.properties.endDate.date.start}")
                println(incomesInMonth.map { it.properties.name.title.fold("") { names, income -> "$names\n${income.plainText}" } })
            }
        }
    }
}
