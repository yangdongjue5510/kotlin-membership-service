package yangdongjue.membershipservice.barcode

class BarcodeFactoryImpl(val generator: RandomNumberGenerator): BarcodeFactory {

    override fun createBarcode(userId: Long): Barcode {
        return Barcode(generator.generate(10))
    }
}
