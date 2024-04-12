package com.lukaorocha

import com.lukaorocha.Runner.Runner
import com.lukaorocha.config.loadConfig
import com.lukaorocha.providers.NotionProvider

fun main() {
    val config = loadConfig()

    Runner().run(config)

    println("Hello World!")
}
