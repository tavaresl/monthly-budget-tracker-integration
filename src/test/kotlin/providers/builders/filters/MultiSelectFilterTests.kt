package providers.builders.filters

import com.lukaorocha.providers.builders.MultiSelectFilter
import org.amshove.kluent.shouldBeEquivalentTo
import org.junit.jupiter.api.Test

@OptIn(ExperimentalStdlibApi::class)
class MultiSelectFilterTests {

    @Test
    fun `contains with some text resolves in { contains = some_text } `() {
        val filter = MultiSelectFilter()

        filter.contains("Some Text")
        filter.build().shouldBeEquivalentTo(object { val contains = "Some Text" })
    }

    @Test
    fun `doesNotContain with some text resolves in { does_not_contain = some_text } `() {
        val filter = MultiSelectFilter()

        filter.doesNotContain("Some Text")
        filter.build().shouldBeEquivalentTo(object { val does_not_contain = "Some Text" })
    }

    @Test
    fun `empty with default resolves in { is_empty = true } `() {
        val filter = MultiSelectFilter()

        filter.empty()
        filter.build().shouldBeEquivalentTo(object { val is_empty = true })
    }

    @Test
    fun `empty with true default resolves in { is_empty = true } `() {
        val filter = MultiSelectFilter()

        filter.empty(true)
        filter.build().shouldBeEquivalentTo(object { val is_empty = true })
    }

    @Test
    fun `empty with false default resolves in { is_not_empty = true } `() {
        val filter = MultiSelectFilter()

        filter.empty(false)
        filter.build().shouldBeEquivalentTo(object { val is_not_empty = true })
    }
}
