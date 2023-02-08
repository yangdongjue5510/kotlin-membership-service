package yangdongjue.membershipservice.point

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import yangdongjue.membershipservice.point.dto.LogDto
import java.time.LocalDateTime

interface LogRepository : JpaRepository<Log, Long> {

    @Query("""
        select new yangdongjue.membershipservice.point.dto.LogDto(l.logDate, l.type, s.name)
         from Log l
         left join Point p on l.barcode = p.barcode
         left join Shop s on l.shopId = s.id
         where (l.logDate BETWEEN :from and :to)
         and l.barcode = :barcode""")
    fun findLogDtosBy(from: LocalDateTime, to: LocalDateTime, barcode: String): List<LogDto>
}
