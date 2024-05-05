package providers.builders.filters

import com.lukaorocha.providers.builders.FileFilter
import org.amshove.kluent.shouldBeEquivalentTo
import org.junit.jupiter.api.Test

@OptIn(ExperimentalStdlibApi::class)
class FileFilterTests {

    @Test
    fun `empty with default result in object { is_empty = true } `() {
        val filter = FileFilter()

        filter.empty()
        filter.build().shouldBeEquivalentTo(object { val is_empty = true })
    }

    @Test
    fun `empty with true result in object { is_empty = true } `() {
        val filter = FileFilter()

        filter.empty(true)
        filter.build().shouldBeEquivalentTo(object { val is_empty = true })
    }

    @Test
    fun `empty with false result in object { is_not_empty = true } `() {
        val filter = FileFilter()

        filter.empty(false)
        filter.build().shouldBeEquivalentTo(object { val is_not_empty = true })
    }
}
