package com.lukaorocha.config

data class Config(
    val notion: NotionConfig,
)

data class NotionConfig (
    val secretKey: String,
    val host: String,
    val expensesDatabaseId: String,
    val balancesDatabaseId: String,
    val incomesDatabaseId: String,
)
