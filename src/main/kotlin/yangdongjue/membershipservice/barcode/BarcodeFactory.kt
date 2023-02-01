package yangdongjue.membershipservice.barcode

interface BarcodeFactory {

    fun createBarcode(userId: Long): Barcode
}
