package yangdongjue.membershipservice.barcode

class RandomNumberGenerator {
    fun generate(digit: Int): String {
        val builder = StringBuilder()
        for (count in 1..digit) {
            builder.append((0..9).random())
        }
        return builder.toString()
    }
}
