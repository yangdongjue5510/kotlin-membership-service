package yangdongjue.membershipservice.barcode

class Barcode(line: String) {
    init {
        if (line.length != 10 || line.any { char -> !char.isDigit() }) {
            throw BarcodeException("멤버십 바코드는 10자리 숫자여야 합니다. 입력된 문자열 $line")
        }
    }
}
