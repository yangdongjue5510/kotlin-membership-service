package yangdongjue.membershipservice.barcode

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import yangdongjue.membershipservice.barcode.exception.BarcodeRegistryException

class BarcodeRegistryTest {

    @Test
    @DisplayName("바코드 등록 정보는 바코드와 사용자 아이디로 생성한다.")
    fun createBarcodeRegistry() {
        assertDoesNotThrow { BarcodeRegistry(123456789, Barcode("1234567890")) }
    }

    @Test
    @DisplayName("바코드 등록 정보의 사용자 아이디는 반드시 9자리 숫자여야 한다.")
    fun createBarcodeRegistry_exception_over9Digits() {
        assertThrows<BarcodeRegistryException> { BarcodeRegistry(1234567890, Barcode("1234567890")) }
    }
}
