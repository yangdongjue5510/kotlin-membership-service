package yangdongjue.membershipservice.barcode

import jakarta.persistence.Embeddable
import yangdongjue.membershipservice.barcode.exception.BarcodeException

@Embeddable
class Barcode(var line: String) {
    init {
        require(line.length == 10 && line.all(Char::isDigit))
        { throw BarcodeException("멤버십 바코드는 10자리 숫자여야 합니다. 입력된 문자열 $line") }
    }
}
