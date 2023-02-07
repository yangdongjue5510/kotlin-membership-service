package yangdongjue.membershipservice.point

import jakarta.persistence.*
import yangdongjue.membershipservice.point.exception.PointException

@Entity
class Point(
    @Column(nullable = false)
    var amount: Long,
    @Enumerated(value = EnumType.ORDINAL)
    @Column(nullable = false)
    val shopSector: ShopSector,
    @Column(nullable = false)
    val barcode: String,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
) {
    fun consume(amount: Long) {
        require(amount > 0) { throw PointException("포인트를 음수로 소비할 수 없습니다. $amount") }
        require(this.amount > amount) { throw PointException("남은 포인트 금액(${this.amount})보다 더 큰 금액($amount)은 사용할 수 없습니다.") }

        this.amount -= amount
    }

    fun accumulate(amount: Long) {
        require(amount > 0) { throw PointException("포인트를 음수로 소비할 수 없습니다. $amount") }

        this.amount += amount
    }
}
