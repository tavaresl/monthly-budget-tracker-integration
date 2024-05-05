package providers.builders.keys

import com.lukaorocha.providers.builders.PropertyKey
import org.amshove.kluent.shouldBeEquivalentTo
import org.junit.jupiter.api.Test
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalStdlibApi::class)
class PropertyKeyTests {

    @Test
    fun `checkbox resolves in { checkbox = {} }`() {
        val propertyKey = PropertyKey("Some Property")

        propertyKey.checkbox {
            checked()
        }

        propertyKey.build().shouldBeEquivalentTo(object {
            val property = "Some Property"
            val checkbox = object {
                val equals = true
            }
        })
    }

    @Test
    fun `date resolves in { date = {} }`() {
        val propertyKey = PropertyKey("Some Property")
        val calendar = Calendar.getInstance()

        calendar.set(2024, 4, 1)

        propertyKey.date {
            before(calendar.time)
        }

        propertyKey.build().shouldBeEquivalentTo(object {
            val property = "Some Property"
            val date = object {
                val before = calendar.asISO()
            }
        })
    }

    @Test
    fun `files resolves in { files = {} }`() {
        val propertyKey = PropertyKey("Some Property")

        propertyKey.files {
            empty()
        }

        propertyKey.build().shouldBeEquivalentTo(object {
            val property = "Some Property"
            val files = object {
                val is_empty = true
            }
        })
    }

    @Test
    fun `multiSelect resolves in { multi_select = {} }`() {
        val propertyKey = PropertyKey("Some Property")

        propertyKey.multiSelect {
            contains("Some Tag")
        }

        propertyKey.build().shouldBeEquivalentTo(object {
            val property = "Some Property"
            val multi_select = object {
                val contains = "Some Tag"
            }
        })
    }
}

fun Calendar.asISO(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    sdf.timeZone = TimeZone.getTimeZone("UTC")
    return sdf.format(this.time)
}
