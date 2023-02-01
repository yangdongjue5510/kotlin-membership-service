package yangdongjue.membershipservice.acceptance

import io.restassured.RestAssured
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import yangdongjue.membershipservice.barcode.BarcodeRegistry

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BarcodeAcceptanceTest(@LocalServerPort var port:Int) {

    @BeforeEach
    internal fun setUp() {
       RestAssured.port = port
    }

    @Test
    @DisplayName("바코드를 생성한다.")
    fun createBarcode() {
        val barcodeRegistry = RestAssured
            .given()
            .post("/barcode/123456789")
            .then().extract().body().`as`(BarcodeRegistry::class.java)

        assertAll(
            Executable { assertThat(barcodeRegistry.userId).isEqualTo(123456789)},
            Executable { assertThat(barcodeRegistry.barcode).isNotNull }
        )
    }
}
