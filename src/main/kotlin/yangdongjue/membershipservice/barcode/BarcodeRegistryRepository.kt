package yangdongjue.membershipservice.barcode

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface BarcodeRegistryRepository : JpaRepository<BarcodeRegistry, Long> {

    fun findByBarcode(barcode: Barcode): Optional<BarcodeRegistry>
}
