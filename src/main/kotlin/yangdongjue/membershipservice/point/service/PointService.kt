package yangdongjue.membershipservice.point.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import yangdongjue.membershipservice.facade.BarcodeFacade
import yangdongjue.membershipservice.facade.ShopFacade
import yangdongjue.membershipservice.point.Point
import yangdongjue.membershipservice.point.PointRepository
import yangdongjue.membershipservice.point.ShopSector

@Service
class PointService(
    val pointRepository: PointRepository,
    val barcodeFacade: BarcodeFacade,
    val shopFacade: ShopFacade
) {

    @Transactional
    fun saveUp(shopId: Long, barcode: String, amount: Long): Point {
        barcodeFacade.validateBarcodeIsExists(barcode)
        val shopSector = ShopSector.valueOf(shopFacade.findShopTypeById(shopId))
        val point = pointRepository.findByBarcodeAndShopSector(barcode, shopSector)
            .orElseGet { pointRepository.save(Point(0, shopSector, barcode)) }
        point.amount += amount
        return point
    }
}
