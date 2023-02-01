package yangdongjue.membershipservice.barcode

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import yangdongjue.membershipservice.barcode.exception.BarcodeException

class BarcodeTest {

    @Test
    @DisplayName("멤버십 바코드는 10자리 문자열로 만들 수 있다.")
    fun createBarcode() {
        val line = "1234567890"
        assertDoesNotThrow { Barcode(line) }
    }

    @ParameterizedTest
    @ValueSource(strings = ["123456789", "123456789a", "", "          ", "abcdefghij"])
    @DisplayName("멤버십 바코드는 10자리 문자열이 아닌데 만들려면 예외가 발생한다.")
    fun createBarcode_exception_not10NumericLetters(line: String) {
        assertThrows<BarcodeException> { Barcode(line) }
    }
}
