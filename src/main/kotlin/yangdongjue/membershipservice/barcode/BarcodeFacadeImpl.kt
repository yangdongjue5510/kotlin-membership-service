package yangdongjue.membershipservice.barcode

import org.springframework.stereotype.Component
import yangdongjue.membershipservice.barcode.exception.BarcodeException
import yangdongjue.membershipservice.facade.BarcodeFacade

@Component
class BarcodeFacadeImpl(val barcodeRegistryRepository: BarcodeRegistryRepository): BarcodeFacade {

    override fun validateBarcodeIsExists(value: String) {
        barcodeRegistryRepository.findByBarcode(Barcode(value))
            .orElseThrow { BarcodeException("존재하지 않는 바코드 = $value") }
    }
}
