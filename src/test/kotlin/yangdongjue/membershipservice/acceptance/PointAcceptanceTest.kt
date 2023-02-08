package yangdongjue.membershipservice.acceptance

import io.restassured.RestAssured
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import yangdongjue.membershipservice.barcode.Barcode
import yangdongjue.membershipservice.barcode.BarcodeRegistry
import yangdongjue.membershipservice.barcode.BarcodeRegistryRepository
import yangdongjue.membershipservice.point.Point
import yangdongjue.membershipservice.point.PointRepository
import yangdongjue.membershipservice.point.ShopSector
import yangdongjue.membershipservice.point.TransactionType
import yangdongjue.membershipservice.point.dto.LogDto
import yangdongjue.membershipservice.shop.Sector
import yangdongjue.membershipservice.shop.Shop
import yangdongjue.membershipservice.shop.ShopRepository
import java.time.LocalDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PointAcceptanceTest(@LocalServerPort var port: Int) {

    @Autowired
    lateinit var shopRepository: ShopRepository

    @Autowired
    lateinit var barcodeRegistryRepository: BarcodeRegistryRepository

    @Autowired
    lateinit var pointRepository: PointRepository

    @BeforeEach
    internal fun setUp() {
        RestAssured.port = port
        pointRepository.deleteAll()
    }

    @Test
    @DisplayName("처음으로 포인트를 적립한다.")
    fun saveUp_firstTime() {
        val savedShop = shopRepository.save(Shop(Sector.A, "shopA"))
        barcodeRegistryRepository.save(BarcodeRegistry(Barcode("1234567890"), 1L))
        val point = callSaveUpAPI(savedShop, 1000)

        assertAll(
            Executable { Assertions.assertThat(point.barcode).isEqualTo("1234567890") },
            Executable { Assertions.assertThat(point.shopSector).isEqualTo(ShopSector.A) },
            Executable { Assertions.assertThat(point.amount).isEqualTo(1000L) }
        )
    }

    @Test
    @DisplayName("이미 존재하는 포인트에 추가로 포인트를 적립한다.")
    fun saveUp_secondTime() {
        val savedShop = shopRepository.save(Shop(Sector.A, "shopA"))
        barcodeRegistryRepository.save(BarcodeRegistry(Barcode("1234567890"), 1L))

        callSaveUpAPI(savedShop, 1000)
        val point = callSaveUpAPI(savedShop, 1000)

        assertAll(
            Executable { Assertions.assertThat(point.barcode).isEqualTo("1234567890") },
            Executable { Assertions.assertThat(point.shopSector).isEqualTo(ShopSector.A) },
            Executable { Assertions.assertThat(point.amount).isEqualTo(2000L) }
        )
    }

    @Test
    @DisplayName("포인트를 사용한다.")
    fun consume() {
        val savedShop = shopRepository.save(Shop(Sector.A, "shopA"))
        barcodeRegistryRepository.save(BarcodeRegistry(Barcode("1234567890"), 1L))

        callSaveUpAPI(savedShop, 1000)
        val point = callConsumeAPI(savedShop, 1)

        assertAll(
            Executable { Assertions.assertThat(point.barcode).isEqualTo("1234567890") },
            Executable { Assertions.assertThat(point.shopSector).isEqualTo(ShopSector.A) },
            Executable { Assertions.assertThat(point.amount).isEqualTo(999L) }
        )
    }

    @Test
    @DisplayName("포인트 내역을 조회한다.")
    fun findLogsBy() {
        val savedShop = shopRepository.save(Shop(Sector.A, "shopA"))
        barcodeRegistryRepository.save(BarcodeRegistry(Barcode("1234567890"), 1L))

        callSaveUpAPI(savedShop, 1000)
        val from = LocalDateTime.now()
        callSaveUpAPI(savedShop, 1000)
        callConsumeAPI(savedShop, 1)
        val to = LocalDateTime.now()

        val logs = callFindLogs(from, to)

        val depositLog = logs[0]
        val withdrawalLog = logs[1]
        assertAll(
            Executable { Assertions.assertThat(logs.size).isEqualTo(2) },
            Executable { Assertions.assertThat(depositLog.type).isEqualTo(TransactionType.DEPOSIT) },
            Executable { Assertions.assertThat(withdrawalLog.type).isEqualTo(TransactionType.WITHDRAW) }
        )
    }

    private fun callFindLogs(
        from: LocalDateTime?,
        to: LocalDateTime?
    ): MutableList<LogDto> = RestAssured
        .given()
        .get("/point/1234567890?from=${from}&to=${to}")
        .then().extract().body().jsonPath().getList(".", LogDto::class.java)

    private fun callConsumeAPI(savedShop: Shop, amount: Int) =
        RestAssured
            .given()
            .post("/point/1234567890/consumption?shopId=${savedShop.id}&amount=${amount}")
            .then().extract().body().`as`(Point::class.java)

    private fun callSaveUpAPI(savedShop: Shop, amount: Long): Point =
        RestAssured
            .given()
            .post("/point/1234567890/deposit?shopId=${savedShop.id}&amount=${amount}")
            .then().extract().body().`as`(Point::class.java)
}
