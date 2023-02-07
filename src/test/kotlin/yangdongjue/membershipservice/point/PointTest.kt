package yangdongjue.membershipservice.point

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import yangdongjue.membershipservice.point.exception.PointException

internal class PointTest {

    @Test
    @DisplayName("포인트를 소비한다.")
    fun consume() {
        val point = Point(1000, ShopSector.A, "123456789")

        point.consume(500)

        assertThat(point.amount).isEqualTo(500)
    }

    @Test
    @DisplayName("가진 포인트보다 더 많은 양을 소비할 수 없다.")
    fun consume_exception_largerAmountThanHave() {
        val point = Point(1000, ShopSector.A, "123456789")

        assertThrows<PointException> { point.consume(1001) }
    }

    @Test
    @DisplayName("음수 양을 소비할 수 없다.")
    fun consume_exception_negativeAmount() {
        val point = Point(1000, ShopSector.A, "123456789")

        assertThrows<PointException> { point.consume(-1) }
    }

    @Test
    @DisplayName("포인트를 적립한다.")
    fun accumulate() {
        val point = Point(1000, ShopSector.A, "123456789")

        point.accumulate(500)

        assertThat(point.amount).isEqualTo(1500)
    }

    @Test
    @DisplayName("음수 양을 적립할 수 없다.")
    fun accumulate_exception_negativeAmount() {
        val point = Point(1000, ShopSector.A, "123456789")

        assertThrows<PointException> { point.accumulate(-1) }
    }
}
