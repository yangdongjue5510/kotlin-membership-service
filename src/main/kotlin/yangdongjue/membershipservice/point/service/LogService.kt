package yangdongjue.membershipservice.point.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import yangdongjue.membershipservice.point.LogRepository
import yangdongjue.membershipservice.point.TransactionType
import yangdongjue.membershipservice.point.factory.LogFactory
import java.time.LocalDateTime

@Service
class LogService(
    val logRepository: LogRepository,
    val logFactory: LogFactory
) {

    @Transactional
    fun add(barcode: String, shopId: Long, type: TransactionType) =
        logRepository.save(logFactory.create(shopId, barcode, type))

    @Transactional(readOnly = true)
    fun findBy(barcode: String, from: LocalDateTime, to: LocalDateTime) =
        logRepository.findLogDtosBy(from, to, barcode)
}
