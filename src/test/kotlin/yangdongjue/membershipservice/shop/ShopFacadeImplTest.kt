package yangdongjue.membershipservice.shop

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import yangdongjue.membershipservice.shop.exception.ShopException

@DataJpaTest
internal class ShopFacadeImplTest {

    @Autowired
    lateinit var shopRepository: ShopRepository
    lateinit var shopFacade: ShopFacadeImpl
    @BeforeEach
    internal fun setUp() {
        shopFacade = ShopFacadeImpl(shopRepository)
    }

    @Test
    @DisplayName("저장된 상점의 업종을 조회한다.")
    fun findShopTypeById() {
        val shop = shopRepository.save(Shop(Sector.A, "shopA"))
        val shopType = shopFacade.findShopTypeById(shop.id)
        assertThat(shopType).isEqualTo("A")

    }
    @Test
    @DisplayName("존재하지 않는 상점의 id로 조회시 예외가 발생한다.")
    fun findShopTypeById_exception_notExists() {
        assertThrows<ShopException> { shopFacade.findShopTypeById(1L) }
    }
}
