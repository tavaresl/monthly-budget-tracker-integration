package providers.builders.filters

import com.lukaorocha.providers.builders.CheckboxFilter
import org.amshove.kluent.*
import org.junit.jupiter.api.Test

@OptIn(ExperimentalStdlibApi::class)
class CheckboxFilterTests {
    @Test
    fun `equals with default result in { equals = true }`() {
        val underTest = CheckboxFilter()

        underTest.checked()
        underTest.build().shouldBeEquivalentTo(object { val equals = true })
    }

    @Test
    fun `equals with true result in { equals = true }`() {
        val underTest = CheckboxFilter()

        underTest.checked(true)
        underTest.build().shouldBeEquivalentTo(object { val equals = true })
    }

    @Test
    fun `equals with false result in { does_not_equal = true }`() {
        val underTest = CheckboxFilter()

        underTest.checked(false)
        underTest.build().shouldBeEquivalentTo(object { val does_not_equal = true })
    }
}
