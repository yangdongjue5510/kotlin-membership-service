package yangdongjue.membershipservice.point.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.function.Executable
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import yangdongjue.membershipservice.facade.BarcodeFacade
import yangdongjue.membershipservice.facade.ShopFacade
import yangdongjue.membershipservice.point.Point
import yangdongjue.membershipservice.point.PointRepository
import yangdongjue.membershipservice.point.ShopSector
import java.util.*

@ExtendWith(MockitoExtension::class)
internal class PointServiceTest {

    @Mock
    lateinit var barcodeFacade: BarcodeFacade

    @Mock
    lateinit var shopFacade: ShopFacade

    @Mock
    lateinit var pointRepository: PointRepository

    @InjectMocks
    lateinit var pointService: PointService

    @Test
    @DisplayName("첫 포인트를 적립한다.")
    fun saveUp_firstTime() {
        val barcode = "1234567890"
        willDoNothing().given(barcodeFacade).validateBarcodeIsExists(barcode)
        `when`(shopFacade.findShopTypeById(1L))
            .thenReturn("A")
        `when`(pointRepository.findByBarcodeAndShopSector(barcode, ShopSector.A))
            .thenReturn(Optional.empty())
        `when`(pointRepository.save(any()))
            .thenReturn(Point(0, ShopSector.A, barcode))

        val point = pointService.saveUp(1L, barcode, 1000L)
        assertAll(
            Executable { assertThat(point.barcode).isEqualTo(barcode) },
            Executable { assertThat(point.amount).isEqualTo(1000L) },
            Executable { assertThat(point.shopSector).isEqualTo(ShopSector.A) }
        )
    }

    @Test
    @DisplayName("기존의 적립된 포인트를 적립한다.")
    fun saveUp_existsPoint() {
        val barcode = "1234567890"
        willDoNothing().given(barcodeFacade).validateBarcodeIsExists(barcode)
        `when`(shopFacade.findShopTypeById(1L))
            .thenReturn("A")
        `when`(pointRepository.findByBarcodeAndShopSector(barcode, ShopSector.A))
            .thenReturn(Optional.of(Point(1000L, ShopSector.A, barcode)))

        val point = pointService.saveUp(1L, barcode, 1000L)

        assertThat(point.amount).isEqualTo(2000L)
    }

    @Test
    @DisplayName("기존의 적립된 포인트를 적립한다.")
    fun consume() {
        val barcode = "1234567890"
        willDoNothing().given(barcodeFacade).validateBarcodeIsExists(barcode)
        `when`(shopFacade.findShopTypeById(1L))
            .thenReturn("A")
        `when`(pointRepository.findByBarcodeAndShopSector(barcode, ShopSector.A))
            .thenReturn(Optional.of(Point(1000L, ShopSector.A, barcode)))

        val point = pointService.consume(1L, barcode, 1)

        assertThat(point.amount).isEqualTo(999L)
    }
}
