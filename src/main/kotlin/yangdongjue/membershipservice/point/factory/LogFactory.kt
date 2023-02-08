package yangdongjue.membershipservice.point.factory

import yangdongjue.membershipservice.point.Log
import yangdongjue.membershipservice.point.TransactionType

interface LogFactory {
    fun create(shopId: Long, barcode: String, type: TransactionType): Log
}
