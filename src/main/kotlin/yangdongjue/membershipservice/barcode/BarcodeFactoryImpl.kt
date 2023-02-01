package yangdongjue.membershipservice.barcode

import org.springframework.stereotype.Component

@Component
class BarcodeFactoryImpl(val generator: RandomNumberGenerator): BarcodeFactory {

    override fun createBarcode(userId: Long): Barcode {
        return Barcode(generator.generate(10))
    }
}
