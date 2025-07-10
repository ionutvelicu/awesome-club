package club.awesome.api.resource

import club.awesome.api.repo.MemberRepo
import club.awesome.api.resource.exception.NotAllowed
import club.awesome.api.resource.exception.NotFoundException
import club.awesome.api.service.PaymentService
import club.awesome.api.service.Utils
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PaymentResource (
    private val utils: Utils,
    private val paymentService: PaymentService,
    private val memberRepo: MemberRepo
) {
    private val log = LoggerFactory.getLogger(ProductResource::class.java)

    @PostMapping("/payment/{id}/create-checkout-session")
    fun createCheckoutSession(@PathVariable id: String): MutableMap<String?, String?> {
        val loggedId = utils.loggedId() ?: throw NotAllowed("not.allowed")
        return paymentService.createCheckoutSession(id, loggedId)
    }

    @PostMapping("/payment/{id}/create-setup-intent")
    fun createSetupIntent(@PathVariable id: String): MutableMap<String?, String?> {
        val loggedId = utils.loggedId() ?: throw NotAllowed("not.allowed")
        return paymentService.createSetupIntent(id, loggedId)
    }

    @GetMapping("/payment/session/{id}/status")
    fun getSessionStatus(@PathVariable id: String): MutableMap<String?, String?> {
        return paymentService.getSessionStatus(id)
    }
}
