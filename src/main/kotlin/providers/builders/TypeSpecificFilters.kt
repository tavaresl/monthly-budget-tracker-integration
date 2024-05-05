package com.lukaorocha.providers.builders

import java.time.Instant
import java.util.Date
import java.text.SimpleDateFormat
import java.util.TimeZone

interface TypeSpecificFilter {
    fun build(): Any
}

class CheckboxFilter: TypeSpecificFilter {
    private var filterKey: String = ""
    private var filterValue: Boolean = false

    fun checked(value: Boolean = true) {
        filterKey = if (value) "equals" else "does_not_equal"
        filterValue = true
    }

    override fun build(): Any {
        return when (filterKey) {
            "equals" -> object { val equals = filterValue }
            "does_not_equal" -> object { val does_not_equal = filterValue }
            else -> throw IllegalArgumentException("Unknown filter key: $filterKey")
        }
    }
}

class DateFilter: TypeSpecificFilter {
    private var filterKey: String = ""
    private var filterValue: Date = Date.from(Instant.now())

    fun after(other: Date) {
        filterKey = "after"
        filterValue = other
    }


    fun before(other: Date) {
        filterKey = "before"
        filterValue = other
    }


    fun eq(other: Date) {
        filterKey = "equals"
        filterValue = other
    }

    override fun build(): Any {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val isoString = sdf.format(filterValue)

        return when (filterKey) {
            "after" -> object { val after = isoString }
            "before" -> object { val before = isoString }
            "equals" -> object { val equals = isoString }
            else -> throw IllegalArgumentException("Unknown filter key: $filterKey")
        }
    }
}

class FileFilter: TypeSpecificFilter {
    private var filterKey: FilterKeys = FilterKeys.IS_EMPTY
    private var filterValue: Boolean = false

    enum class FilterKeys(val value: String) {
        IS_EMPTY("is_empty"),
        IS_NOT_EMPTY("is_not_empty"),
    }

    fun empty(value: Boolean = true) {
        filterKey = if (value) FilterKeys.IS_EMPTY else FilterKeys.IS_NOT_EMPTY
        filterValue = true
    }

    override fun build(): Any {
        return when (filterKey) {
            FilterKeys.IS_EMPTY -> object { val is_empty = filterValue }
            FilterKeys.IS_NOT_EMPTY -> object { val is_not_empty = filterValue }
        }
    }
}

class MultiSelectFilter: TypeSpecificFilter {
    private var filterKey: FilterKeys = FilterKeys.IS_EMPTY
    private var filterValue: String = ""

    enum class FilterKeys(val value: String) {
        CONTAINS("contains"),
        DOES_NOT_CONTAIN("does_not_contain"),
        IS_EMPTY("is_empty"),
        IS_NOT_EMPTY("is_not_empty"),
    }

    fun contains(tag: String) {
        filterKey = FilterKeys.CONTAINS
        filterValue = tag
    }

    fun doesNotContain(tag: String) {
        filterKey = FilterKeys.DOES_NOT_CONTAIN
        filterValue = tag
    }

    fun empty(value: Boolean = true) {
        filterKey = if (value) FilterKeys.IS_EMPTY else FilterKeys.IS_NOT_EMPTY
    }

    override fun build(): Any {
        return when (filterKey) {
            FilterKeys.CONTAINS -> object { val contains = filterValue }
            FilterKeys.DOES_NOT_CONTAIN -> object { val does_not_contain = filterValue }
            FilterKeys.IS_EMPTY -> object { val is_empty = true }
            FilterKeys.IS_NOT_EMPTY -> object { val is_not_empty = true }
        }
    }
}
