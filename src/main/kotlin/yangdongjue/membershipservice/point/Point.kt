package yangdongjue.membershipservice.point

import jakarta.persistence.*

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
)
