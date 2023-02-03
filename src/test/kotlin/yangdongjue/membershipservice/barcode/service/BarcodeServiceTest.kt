package yangdongjue.membershipservice.barcode.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import yangdongjue.membershipservice.barcode.*
import yangdongjue.membershipservice.barcode.exception.BarcodeException
import java.util.*

@ExtendWith(MockitoExtension::class)
internal class BarcodeServiceTest {

    @Mock
    lateinit var barcodeRegistryRepository: BarcodeRegistryRepository

    @Mock
    lateinit var barcodeFactory: BarcodeFactory

    @InjectMocks
    lateinit var barcodeService: BarcodeService

    @Test
    @DisplayName("사용자의 아이디를 받아 바코드를 생성한다.")
    fun createBarcode() {
        val userId = 123456789L
        val barcode = Barcode("1234567890")
        val barcodeRegistry = BarcodeRegistry(barcode, userId)
        `when`(barcodeRegistryRepository.findById(userId))
            .thenReturn(Optional.empty())
        `when`(barcodeFactory.createBarcode(userId))
            .thenReturn(barcode)
        `when`(barcodeRegistryRepository.findByBarcode(barcode))
            .thenReturn(Optional.empty())
        `when`(barcodeRegistryRepository.save(any()))
            .thenReturn(barcodeRegistry)

        val createdBarcodeRegistry = barcodeService.createBarcode(userId)
        assertThat(createdBarcodeRegistry).isEqualTo(barcodeRegistry)
    }

    @Test
    @DisplayName("생성된 바코드가 이미 존재하는 경우 예외가 발생한다.")
    fun createBarcode_exception_existsBarcode() {
        val userId = 123456789L
        val barcode = Barcode("1234567890")
        val barcodeRegistry = BarcodeRegistry(barcode, userId)
        `when`(barcodeRegistryRepository.findById(userId))
            .thenReturn(Optional.empty())
        `when`(barcodeFactory.createBarcode(userId))
            .thenReturn(barcode)
        `when`(barcodeRegistryRepository.findByBarcode(barcode))
            .thenReturn(Optional.of(barcodeRegistry))

        assertThrows<BarcodeException> { barcodeService.createBarcode(userId) }
    }

}
