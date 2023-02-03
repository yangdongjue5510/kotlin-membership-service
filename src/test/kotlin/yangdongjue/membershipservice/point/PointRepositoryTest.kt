package yangdongjue.membershipservice.point

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class PointRepositoryTest {

    @Autowired
    lateinit var pointRepository: PointRepository

    @Test
    @DisplayName("바코드와 업종에 해당하는 포인트 정보를 가져온다.")
    fun findByBarcodeAndShopSector() {
        pointRepository.save(Point(1000L, ShopSector.A, "1234567890"))
        val pointOptional = pointRepository.findByBarcodeAndShopSector("1234567890", ShopSector.A)
        assertThat(pointOptional).isNotEmpty
    }
}
