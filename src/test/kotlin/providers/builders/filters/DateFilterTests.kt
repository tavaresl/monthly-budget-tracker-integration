package providers.builders.filters

import com.lukaorocha.providers.builders.DateFilter
import org.amshove.kluent.shouldBeEquivalentTo
import org.junit.jupiter.api.Test
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.TimeZone

@OptIn(ExperimentalStdlibApi::class)
class DateFilterTests {
    companion object {
        val cal: Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

        fun asISO(): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            return sdf.format(cal.time)
        }
    }

    @Test
    fun `after should result in a pair with after and the iso string representation of provided date`() {
        val filter = DateFilter()
        cal.set(2024,3,1, 0,0,0)

        filter.after(cal.time)
        filter.build().shouldBeEquivalentTo(object { val after = asISO() })
    }

    @Test
    fun `before should result in a pair with before and the iso string representation of provided date`() {
        val filter = DateFilter()
        cal.set(2024,3,1, 0,0,0)

        filter.before(cal.time)
        filter.build().shouldBeEquivalentTo(object { val before = asISO() })
    }

    @Test
    fun `equals should result in a pair with equals and the iso string representation of provided date`() {
        val filter = DateFilter()
        cal.set(2024,3,1, 0,0,0)

        filter.before(cal.time)
        filter.build().shouldBeEquivalentTo(object { val before = asISO() })
    }
}
