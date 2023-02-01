package yangdongjue.membershipservice.barcode

import yangdongjue.membershipservice.barcode.exception.BarcodeRegistryException

class BarcodeRegistry(userId: Long, val barcode: Barcode) {
    init {
        if (userId < 0 || userId.toString().length > 9) throw BarcodeRegistryException("바코드를 등록할 때 사용자의 아이디는 9자리 숫자여야 합니다. 입력된 값 : $userId")
    }
}
