package yangdongjue.membershipservice.shop

import jakarta.persistence.*

@Entity
class Shop(
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    val sector: Sector,
    @Column(nullable = false)
    val name: String,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
)
