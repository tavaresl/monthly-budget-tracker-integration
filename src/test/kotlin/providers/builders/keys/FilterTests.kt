package providers.builders.keys

import com.fasterxml.jackson.databind.ObjectMapper
import com.lukaorocha.providers.builders.Filter
import com.lukaorocha.providers.builders.filter
import org.amshove.kluent.shouldBeEquivalentTo
import org.junit.jupiter.api.Test
import java.util.*

@OptIn(ExperimentalStdlibApi::class)
class FilterTests {

    @Test
    fun `and result in { filter = { and } }`() {
        val filter = Filter()
        filter.and {  }

        filter.build().shouldBeEquivalentTo(object {
            val filter = object { val and = emptyList<Any>() }
        })
    }

    @Test
    fun `or result in { filter = { or } }`() {
        val filter = Filter()
        filter.or {  }

        filter.build().shouldBeEquivalentTo(object {
            val filter = object { val or = emptyList<Any>() }
        })
    }

    @Test
    fun `property result in { filter = { property } }`() {
        val filter = Filter()
        filter.property("Some Property") {
            checkbox {
                checked()
            }
        }

        filter.build().shouldBeEquivalentTo(object {
            val filter = object {
                val property = "Some Property"
                val checkbox = object {
                    val equals = true
                }
            }
        })
    }

    @Test
    fun `builder result in { filter = { } }`() {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.set(2024,4,1,0,0,0)

        val filterObj = filter {
            or {
                property("Required") {
                    checkbox {
                        checked()
                    }
                }
                and {
                    property("Created At") {
                        date {
                            after(calendar.time)
                        }
                    }
                    property("Required") {
                        checkbox {
                            checked(false)
                        }
                    }
                }
            }
        }

        filterObj.build().shouldBeEquivalentTo(object {
            val filter = object {
                val or = listOf(
                    object {
                        val property = "Required"
                        val checkbox = object {
                            val equals = true
                        }
                    },
                    object {
                        val and = listOf(
                            object {
                                val property = "Created At"
                                val date = object {
                                    val after = calendar.asISO()
                                }
                            },
                            object {
                                val property = "Required"
                                val checkbox = object {
                                    val does_not_equal = true
                                }
                            }
                        )
                    }
                )
            }
        })
    }
}
