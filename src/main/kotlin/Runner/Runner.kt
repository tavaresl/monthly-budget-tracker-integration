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
            }
        }
    }
}
