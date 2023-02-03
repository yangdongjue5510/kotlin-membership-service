package yangdongjue.membershipservice.shop

import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.stereotype.Component
import yangdongjue.membershipservice.facade.ShopFacade
import yangdongjue.membershipservice.shop.exception.ShopException

@Component
class ShopFacadeImpl(val shopRepository: ShopRepository): ShopFacade {

    override fun findShopTypeById(shopId: Long): String {
        return  shopRepository.findById(shopId).orElseThrow { ShopException("존재하지 않는 상점 아이디 = $id") }
            .sector.name
    }
}
