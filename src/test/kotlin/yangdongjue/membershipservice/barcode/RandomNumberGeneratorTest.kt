package yangdongjue.membershipservice.barcode

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

class RandomNumberGeneratorTest {

    @Test
    @DisplayName("입력받은 자릿수만큼 랜덤한 숫자 문자열을 만든다.")
    fun generateNumbers() {
        val generator = RandomNumberGenerator()
        val line = generator.generate(10)
        val anotherLine = generator.generate(10)

        assertAll(
            Executable { assertThat(line.length).isEqualTo(10) },
            Executable { assertThat(anotherLine.length).isEqualTo(10) },
            Executable { assertThat(line).isNotEqualTo(anotherLine) }
        )
    }
}
