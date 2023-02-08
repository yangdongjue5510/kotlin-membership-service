package yangdongjue.membershipservice.point.service

import org.springframework.stereotype.Component
import yangdongjue.membershipservice.point.Log
import yangdongjue.membershipservice.point.TransactionType
import yangdongjue.membershipservice.point.factory.LogFactory
import java.time.LocalDateTime

@Component
class LogFactoryImpl: LogFactory {
    override fun create(shopId: Long, barcode: String, type: TransactionType) =
        Log(LocalDateTime.now(), type, shopId, barcode)
}
