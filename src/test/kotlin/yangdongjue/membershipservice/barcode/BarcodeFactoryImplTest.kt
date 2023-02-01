package yangdongjue.membershipservice.barcode

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class BarcodeFactoryImplTest {

    @Test
    @DisplayName("사용자 아이디를 전달해서 바코드를 생성한다.")
    fun createBarcode() {
        val factory = BarcodeFactoryImpl(RandomNumberGenerator())
        assertDoesNotThrow { factory.createBarcode(123456789) }
    }
}
