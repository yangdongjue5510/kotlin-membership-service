package yangdongjue.membershipservice.barcode

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import yangdongjue.membershipservice.barcode.exception.BarcodeException

@DataJpaTest
internal class BarcodeFacadeImplTest {

    @Autowired
    lateinit var barcodeRegistryRepository: BarcodeRegistryRepository
    lateinit var barcodeFacadeImpl: BarcodeFacadeImpl

    @BeforeEach
    internal fun setUp() {
        barcodeFacadeImpl = BarcodeFacadeImpl(barcodeRegistryRepository)
    }


    @Test
    @DisplayName("존재하지 않는 바코드는 예외를 반환한다.")
    fun validateBarcode_exception_notExists() {
        assertThrows<BarcodeException> { barcodeFacadeImpl.validateBarcodeIsExists("notExists") }
    }
}
