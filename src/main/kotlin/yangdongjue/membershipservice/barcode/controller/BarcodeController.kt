package yangdongjue.membershipservice.barcode.controller

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import yangdongjue.membershipservice.barcode.BarcodeRegistry
import yangdongjue.membershipservice.barcode.service.BarcodeService

@RestController
class BarcodeController(val barcodeService: BarcodeService) {

    @PostMapping("/barcode/{userId}")
    fun createBarcode(@PathVariable userId: Long): BarcodeRegistry {
       return barcodeService.createBarcode(userId)
    }
}
