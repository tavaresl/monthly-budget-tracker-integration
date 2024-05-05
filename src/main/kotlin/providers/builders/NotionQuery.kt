package com.lukaorocha.providers.builders

interface FilterKey {
    fun build(): Any
}

abstract class CompoundFilterKey : FilterKey {
    protected val keys: ArrayList<FilterKey> = ArrayList()

    protected fun <T : FilterKey> initKey(key: T, init: T.() -> Unit): T {
        key.init()
        keys.add(key)
        return key
    }

    abstract fun and(init: AndKey.() -> Unit): CompoundFilterKey
    abstract fun or(init: OrKey.() -> Unit): CompoundFilterKey
    abstract fun property(propertyName: String, init: PropertyKey.() -> Unit): FilterKey
}

class AndKey: CompoundFilterKey() {
    override fun and(init: AndKey.() -> Unit) = initKey(AndKey(), init)
    override fun or(init: OrKey.() -> Unit) = initKey(OrKey(), init)
    override fun property(propertyName: String, init: PropertyKey.() -> Unit) = initKey(PropertyKey(propertyName), init)

    override fun build(): Any {
        return object {
            val and = keys.map { it.build() }
        }
    }
}

class OrKey: CompoundFilterKey() {
    override fun and(init: AndKey.() -> Unit) = initKey(AndKey(), init)
    override fun or(init: OrKey.() -> Unit) = initKey(OrKey(), init)
    override fun property(propertyName: String, init: PropertyKey.() -> Unit) = initKey(PropertyKey(propertyName), init)

    override fun build(): Any {
        return object {
            val or = keys.map { it.build() }
        }
    }
}

class PropertyKey(val propertyName: String): FilterKey {
    var typeKey: PropertyTypes? = null
    var typeFilter: TypeSpecificFilter? = null

    enum class PropertyTypes(val type: String) {
        CHECKBOX("checkbox"),
        DATE("date"),
        MULTI_SELECT("multi_select"),
        FILES("files"),
    }

    fun checkbox(init: CheckboxFilter.() -> Unit): CheckboxFilter {
        typeKey = PropertyTypes.CHECKBOX
        typeFilter = CheckboxFilter()
        (typeFilter as CheckboxFilter).init()
        return typeFilter as CheckboxFilter
    }

    fun date(init: DateFilter.() -> Unit): DateFilter {
        typeKey = PropertyTypes.DATE
        typeFilter = DateFilter()
        (typeFilter as DateFilter).init()
        return typeFilter as DateFilter
    }

    fun files(init: FileFilter.() -> Unit): FileFilter {
        typeKey = PropertyTypes.FILES
        typeFilter = FileFilter()
        (typeFilter as FileFilter).init()
        return typeFilter as FileFilter
    }

    fun multiSelect(init: MultiSelectFilter.() -> Unit): MultiSelectFilter {
        typeKey = PropertyTypes.MULTI_SELECT
        typeFilter = MultiSelectFilter()
        (typeFilter as MultiSelectFilter).init()
        return typeFilter as MultiSelectFilter
    }

//    fun formula() {}
//    fun number() {}
//    fun people() {}
//    fun phoneNumber() {}
//    fun relation() {}
//    fun richText() {}
//    fun select() {}
//    fun status() {}
//    fun timestamp() {}
//    fun id() {}

    override fun build(): Any {
        return when (typeKey) {
            PropertyTypes.CHECKBOX -> object {
                val property = propertyName
                val checkbox = typeFilter?.build()
            }
            PropertyTypes.DATE -> object {
                val property = propertyName
                val date = typeFilter?.build()
            }
            PropertyTypes.MULTI_SELECT -> object {
                val property = propertyName
                val multi_select = typeFilter?.build()
            }
            PropertyTypes.FILES -> object {
                val property = propertyName
                val files = typeFilter?.build()
            }
            null -> TODO()
        }
    }
}

class Filter {
    private var filter: FilterKey? = null

    private fun <T : FilterKey> initFilter(filter: T, init: T.() -> Unit): T {
        filter.init()
        this.filter = filter
        return filter
    }

    fun and(init: AndKey.() -> Unit) = initFilter(AndKey(), init)
    fun or(init: OrKey.() -> Unit) = initFilter(OrKey(), init)
    fun property(propertyName: String, init: PropertyKey.() -> Unit) = initFilter(PropertyKey(propertyName), init)

    fun build(): Any {
        return object {
            val filter = this@Filter.filter?.build()
        }
    }
}

fun filter(init: Filter.() -> Unit): Filter {
    val filter = Filter()
    filter.init()
    return filter
}
