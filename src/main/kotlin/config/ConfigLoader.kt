package com.lukaorocha.config

import com.typesafe.config.ConfigFactory

fun loadConfig(): Config {
    val config = ConfigFactory.systemEnvironment()
        .withFallback(ConfigFactory.load("application.conf"))

    return Config(
        NotionConfig(
            secretKey = config.getString("notion.secretKey"),
            host = config.getString("notion.host"),
            balancesDatabaseId = config.getString("notion.balancesDatabaseId"),
            expensesDatabaseId = config.getString("notion.expensesDatabaseId"),
            incomesDatabaseId = config.getString("notion.incomesDatabaseId"),
        )
    )
}

