package providers.builders.keys

import com.lukaorocha.providers.builders.AndKey
import com.lukaorocha.providers.builders.CompoundFilterKey
import org.amshove.kluent.shouldBeEquivalentTo
import org.junit.jupiter.api.Test

@OptIn(ExperimentalStdlibApi::class)
class CompoundFilterKeyTests {

    @Test
    fun `property result in a list with one more PropertyKey `() {
        val andKey: CompoundFilterKey = AndKey()

        andKey.property("Some Property") {
            checkbox {
                checked()
            }
        }

        val result = andKey.build()
            result.shouldBeEquivalentTo(object {
            val and = listOf<Any>(object {
                val property = "Some Property"
                val checkbox = object {
                    val equals = true
                }
            })
        })
    }

    @Test
    fun `and result in a list with one more AndKey `() {
        val andKey: CompoundFilterKey = AndKey()

        andKey.and {  }

        val result = andKey.build()
        result.shouldBeEquivalentTo(object {
            val and = listOf<Any>(object {
                val and = emptyList<Any>()
            })
        })
    }

    @Test
    fun `or result in a list with one more OrKey `() {
        val andKey: CompoundFilterKey = AndKey()

        andKey.or {  }

        val result = andKey.build()
        result.shouldBeEquivalentTo(object {
            val and = listOf<Any>(object {
                val or = emptyList<Any>()
            })
        })
    }
}
