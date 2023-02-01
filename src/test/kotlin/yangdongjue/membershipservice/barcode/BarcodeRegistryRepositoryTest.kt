package yangdongjue.membershipservice.barcode

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
internal class BarcodeRegistryRepositoryTest {

    @Autowired
    lateinit var barcodeRegistryRepository: BarcodeRegistryRepository

    @Test
    @DisplayName("바코드와 일치한 바코드 등록 정보를 조회한다.")
    fun findByBarcode() {
        val barcode = Barcode("1234567890")
        barcodeRegistryRepository.save(BarcodeRegistry(barcode, 123456789))

        val foundBarcode = barcodeRegistryRepository.findByBarcode(barcode)
        assertThat(foundBarcode.orElseThrow().barcode.line)
            .isEqualTo(barcode.line)
    }
}
