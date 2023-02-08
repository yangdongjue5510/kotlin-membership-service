package yangdongjue.membershipservice.point.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import yangdongjue.membershipservice.facade.BarcodeFacade
import yangdongjue.membershipservice.facade.ShopFacade
import yangdongjue.membershipservice.point.Point
import yangdongjue.membershipservice.point.PointRepository
import yangdongjue.membershipservice.point.ShopSector
import yangdongjue.membershipservice.point.TransactionType
import yangdongjue.membershipservice.point.exception.PointException

@Service
class PointService(
    val pointRepository: PointRepository,
    val logService: LogService,
    val barcodeFacade: BarcodeFacade,
    val shopFacade: ShopFacade
) {

    @Transactional
    fun saveUp(shopId: Long, barcode: String, amount: Long): Point {
        barcodeFacade.validateBarcodeIsExists(barcode)
        val shopSector = ShopSector.valueOf(shopFacade.findShopTypeById(shopId))
        val point = pointRepository.findByBarcodeAndShopSector(barcode, shopSector)
            .orElseGet { pointRepository.save(Point(0, shopSector, barcode)) }
        point.accumulate(amount)
        logService.add(barcode, shopId, TransactionType.DEPOSIT)
        return point
    }

    @Transactional
    fun consume(shopId: Long, barcode: String, amount: Long): Point {
        barcodeFacade.validateBarcodeIsExists(barcode)
        val shopSector = ShopSector.valueOf(shopFacade.findShopTypeById(shopId))
        val point = pointRepository.findByBarcodeAndShopSector(barcode, shopSector)
            .orElseThrow { PointException("일치하는 포인트 적립 정보가 없습니다. 상점ID = $shopId, 바코드 = $barcode") }
        point.consume(amount)
        logService.add(barcode, shopId, TransactionType.WITHDRAW)
        return point
    }
}
