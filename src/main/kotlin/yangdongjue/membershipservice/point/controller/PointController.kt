package yangdongjue.membershipservice.point.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import yangdongjue.membershipservice.point.service.LogService
import yangdongjue.membershipservice.point.service.PointService
import java.time.LocalDateTime

@RestController
class PointController(
    val pointService: PointService,
    val logService: LogService
    ) {

    @PostMapping("/point/{barcode}/deposit")
    fun saveUp(
        @PathVariable barcode: String,
        @RequestParam(required = true) shopId: Long,
        @RequestParam(required = true) amount: Long
    ) = pointService.saveUp(shopId, barcode, amount)

    @PostMapping("/point/{barcode}/consumption")
    fun consume(
        @PathVariable barcode: String,
        @RequestParam(required = true) shopId: Long,
        @RequestParam(required = true) amount: Long
    ) = pointService.consume(shopId, barcode, amount)

    @GetMapping("/point/{barcode}")
    fun findLogs(
        @PathVariable barcode: String,
        @RequestParam(required = true) from: LocalDateTime,
        @RequestParam(required = true) to: LocalDateTime
    ) = logService.findBy(barcode, from, to)
}
