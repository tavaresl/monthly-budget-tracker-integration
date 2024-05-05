package com.lukaorocha.providers

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.module.kotlin.jsonMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.lukaorocha.config.Config
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.*

class NotionProvider(private val config: Config) {
    private val client = OkHttpClient()
    private val mapper: ObjectMapper = jsonMapper {
        addModule(kotlinModule())
        propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    fun fetchExpenses(): List<Page<ExpensePageProperties>> {
        return all<ExpensePageProperties>(config.notion.expensesDatabaseId)
    }

    fun fetchIncomes(): List<Page<IncomePageProperties>> {
        return all<IncomePageProperties>(config.notion.incomesDatabaseId)
    }

    fun fetchMonthlyBalances(): List<Page<MonthlyBalancePageProperties>> {
        return all<MonthlyBalancePageProperties>(config.notion.balancesDatabaseId)
    }

    private inline fun <reified T> all(databaseId: String): List<Page<T>> {
        val pages = ArrayList<Page<T>>()
        var cursor: UUID? = null

        do {
            val body =
                if (cursor == null) {
                    object { }
                } else {
                    object { val start_cursor = cursor }
                }

            val request = Request.Builder()
                .url("${config.notion.host}/v1/databases/${databaseId}/query")
                .method("POST", mapper.writeValueAsString(body).toRequestBody("application/json".toMediaType()))
                .header("Authorization", "Bearer ${config.notion.secretKey}")
                .header("Notion-Version", "2022-06-28")
                .build()

            val response = client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                val responseBody = response.body!!.string()
                mapper.readValue<PaginatedResponse<Page<T>>>(responseBody)
            }

            pages.addAll(response.results)
            cursor = response.nextCursor
        } while (cursor != null)

        return pages
    }
}

fun notionProvider(config: Config, init: NotionProvider.() -> Unit): NotionProvider {
    val notionProvider = NotionProvider(config)
    notionProvider.init()
    return notionProvider
}
