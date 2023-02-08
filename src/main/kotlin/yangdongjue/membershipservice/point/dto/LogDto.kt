package yangdongjue.membershipservice.point.dto

import yangdongjue.membershipservice.point.TransactionType
import java.time.LocalDateTime

data class LogDto(val logDate: LocalDateTime, val type: TransactionType, val shopName: String)
