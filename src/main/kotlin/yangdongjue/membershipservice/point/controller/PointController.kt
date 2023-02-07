package yangdongjue.membershipservice.point.controller

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import yangdongjue.membershipservice.point.Point
import yangdongjue.membershipservice.point.service.PointService

@RestController
class PointController(val pointService: PointService) {

    @PostMapping("/point/{barcode}/deposit")
    fun saveUp(
        @PathVariable barcode: String,
        @RequestParam(required = true) shopId: Long,
        @RequestParam(required = true) amount: Long
    ): Point {
        return pointService.saveUp(shopId, barcode, amount)
    }

    @PostMapping("/point/{barcode}/consumption")
    fun consume(
        @PathVariable barcode: String,
        @RequestParam(required = true) shopId: Long,
        @RequestParam(required = true) amount: Long
    ): Point {
        return pointService.consume(shopId, barcode, amount)
    }
}
