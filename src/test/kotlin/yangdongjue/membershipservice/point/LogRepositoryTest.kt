package yangdongjue.membershipservice.point

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import yangdongjue.membershipservice.barcode.Barcode
import yangdongjue.membershipservice.barcode.BarcodeRegistry
import yangdongjue.membershipservice.barcode.BarcodeRegistryRepository
import yangdongjue.membershipservice.shop.Sector
import yangdongjue.membershipservice.shop.Shop
import yangdongjue.membershipservice.shop.ShopRepository
import java.time.LocalDateTime

@DataJpaTest
internal class LogRepositoryTest(
) {

    @Autowired
    lateinit var logRepository: LogRepository

    @Autowired
    lateinit var barcodeRegistryRepository: BarcodeRegistryRepository

    @Autowired
    lateinit var shopRepository: ShopRepository

    @Test
    @DisplayName("날짜와 바코드에 해당하는 로그를 조회한다.")
    fun findByLogsBy() {
        val shop = shopRepository.save(Shop(Sector.A, "shopA"))
        val barcodeValue = "1234567890"
        barcodeRegistryRepository.save(BarcodeRegistry(Barcode(barcodeValue), 1))
        val logOfDay1 = Log(
            LocalDateTime.of(2000, 1, 1, 1, 1),
            TransactionType.DEPOSIT,
            shop.id,
            barcodeValue
        )
        val logOfDay3 = Log(
            LocalDateTime.of(2000, 1, 3, 1, 1),
            TransactionType.DEPOSIT,
            shop.id,
            barcodeValue
        )
        val logOfDay5 = Log(
            LocalDateTime.of(2000, 1, 5, 1, 1),
            TransactionType.DEPOSIT,
            shop.id,
            barcodeValue
        )
        logRepository.saveAll(listOf(logOfDay1, logOfDay3, logOfDay5))


        val day2 = LocalDateTime.of(2000, 1, 2, 1, 1)
        val day4 = LocalDateTime.of(2000, 1, 4, 1, 1)

        val logs = logRepository.findLogDtosBy(day2, day4, barcodeValue)

        assertThat(logs.size).isEqualTo(1)
    }
}
