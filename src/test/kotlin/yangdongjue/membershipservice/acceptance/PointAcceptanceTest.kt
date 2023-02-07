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
import yangdongjue.membershipservice.shop.Sector
import yangdongjue.membershipservice.shop.Shop
import yangdongjue.membershipservice.shop.ShopRepository

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
        val savedShop = shopRepository.save(Shop(Sector.A))
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
        val savedShop = shopRepository.save(Shop(Sector.A))
        barcodeRegistryRepository.save(BarcodeRegistry(Barcode("1234567890"), 1L))

        callSaveUpAPI(savedShop, 1000)
        val point = callSaveUpAPI(savedShop, 1000)

        assertAll(
            Executable { Assertions.assertThat(point.barcode).isEqualTo("1234567890") },
            Executable { Assertions.assertThat(point.shopSector).isEqualTo(ShopSector.A) },
            Executable { Assertions.assertThat(point.amount).isEqualTo(2000L) }
        )
    }

    private fun callSaveUpAPI(savedShop: Shop, amount: Long): Point =
        RestAssured
            .given()
            .post("/point/1234567890?shopId=${savedShop.id}&amount=${amount}")
            .then().extract().body().`as`(Point::class.java)
}
