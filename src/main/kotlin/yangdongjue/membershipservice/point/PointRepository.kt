package yangdongjue.membershipservice.point

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PointRepository: JpaRepository<Point, String> {
    fun findByBarcodeAndShopSector(barcode: String, shopSector: ShopSector): Optional<Point>
}
