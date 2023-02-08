package yangdongjue.membershipservice.point

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@Entity
@Table(name = "point_log")
class Log(
    @Column(nullable = false)
    @CreatedDate
    var logDate: LocalDateTime,
    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    val type: TransactionType,
    @Column(nullable = false)
    val shopId: Long,
    @Column(nullable = false)
    val barcode: String,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
)
