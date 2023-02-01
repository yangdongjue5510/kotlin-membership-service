package yangdongjue.membershipservice.barcode.service

import org.springframework.stereotype.Service
import yangdongjue.membershipservice.barcode.BarcodeFactory
import yangdongjue.membershipservice.barcode.BarcodeRegistry
import yangdongjue.membershipservice.barcode.BarcodeRegistryRepository
import yangdongjue.membershipservice.barcode.exception.BarcodeException

@Service
class BarcodeService(val barcodeRegistryRepository: BarcodeRegistryRepository, val barcodeFactory: BarcodeFactory) {

    fun createBarcode(userId: Long): BarcodeRegistry {
        return barcodeRegistryRepository.findById(userId)
            .orElseGet { createAndSaveBarcodeRegistry(userId) }
    }

    private fun createAndSaveBarcodeRegistry(userId: Long): BarcodeRegistry {
        val barcode = barcodeFactory.createBarcode(userId)
        barcodeRegistryRepository.findByBarcode(barcode)
            .ifPresent { throw BarcodeException("이미 존재하는 바코드가 생성되었습니다.") }
        return barcodeRegistryRepository.save(BarcodeRegistry(barcode, userId))
    }
}
