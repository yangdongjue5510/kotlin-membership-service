package yangdongjue.membershipservice.barcode

import yangdongjue.membershipservice.barcode.exception.RandomNumberDigitException
import java.util.*
import kotlin.math.abs

class RandomNumberGenerator {
    fun generate(digit: Int): String {
        if (digit > 18 || digit < 1) throw RandomNumberDigitException("난수는 1자 이상, 18자 이하로만 만들 수 있습니다. 입력 값 : $digit")
        return abs(UUID.randomUUID().mostSignificantBits + Random().nextInt())
            .toString().padStart(18, '0')
            .substring(0, digit)
    }
}
