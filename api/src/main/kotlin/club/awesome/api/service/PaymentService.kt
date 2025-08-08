package club.awesome.api.service

import club.awesome.api.repo.MemberRepo
import club.awesome.api.repo.ProductRepo
import club.awesome.api.resource.exception.NotFoundException
import com.stripe.Stripe
import com.stripe.model.Customer
import com.stripe.model.PaymentIntent
import com.stripe.model.Price
import com.stripe.model.checkout.Session
import com.stripe.param.CustomerCreateParams
import com.stripe.param.PaymentIntentCreateParams
import com.stripe.param.PriceCreateParams
import com.stripe.param.ProductCreateParams
import com.stripe.param.checkout.SessionCreateParams
import org.slf4j.LoggerFactory
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import java.util.*
import com.stripe.model.Product as StripeProduct

@Service
class PaymentService(
    private val utils: Utils,
    private val productRepo: ProductRepo,
    private val memberRepo: MemberRepo,
    private val environment: Environment
) {
    private val log = LoggerFactory.getLogger(ProductService::class.java)

    fun createCheckoutSession(productId: String, ownerId: String): MutableMap<String?, String?> {
        configureStripe()

        val product = productRepo.findOneById(productId) ?: throw NotFoundException("product.not.found")
        val member = memberRepo.findOneById(ownerId) ?: throw NotFoundException("member.not.found")

        val YOUR_DOMAIN = "http://localhost:4321/"
        val stripeProduct = StripeProduct.create(
            ProductCreateParams.builder()
                .setName(product.name+"--"+product.id)
                .build()
        )

        val price = Price.create(
            PriceCreateParams.builder()
                .setProduct(stripeProduct.id)
                .setCurrency("usd")
                .setUnitAmount((product.price * 100).toLong())
                .build()
        )

        val params: SessionCreateParams? =
            SessionCreateParams.builder()
                .setUiMode(SessionCreateParams.UiMode.CUSTOM)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setReturnUrl(YOUR_DOMAIN + "c/${product.id}/return?session_id={CHECKOUT_SESSION_ID}")
                .addLineItem(
                    SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPrice(price.id)
                        .build()
                )
                .build()

        val session: Session = Session.create(params)

        val map: MutableMap<String?, String?> = HashMap<String?, String?>()
        map.put("clientSecret", session.getRawJsonObject().getAsJsonPrimitive("client_secret").getAsString())
        map.put("clientEmail", member.email)
        return map
    }

    fun createSetupIntent(productId: String, ownerId: String): MutableMap<String?, String?> {
        configureStripe()

        val product = productRepo.findOneById(productId) ?: throw NotFoundException("product.not.found")
        val member = memberRepo.findOneById(ownerId) ?: throw NotFoundException("member.not.found")

        val customer = Customer.create(
            CustomerCreateParams.builder()
            .setEmail(member.email) // dynamically from your app
            .build())

        val params = PaymentIntentCreateParams.builder()
            .setAmount((product.price * 100).toLong()) // e.g. 500 = $5.00
            .setCurrency("usd")
            .setAutomaticPaymentMethods(
                PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                    .setEnabled(true)
                    .build()
            )
            .setSetupFutureUsage(PaymentIntentCreateParams.SetupFutureUsage.OFF_SESSION)
            .setCustomer(customer.id) // ✅ Attach customer to enable Link & saved cards
            .build()

        val intent = PaymentIntent.create(params)

        val map: MutableMap<String?, String?> = HashMap<String?, String?>()
        map.put("clientSecret", intent.clientSecret)
        return map
    }

    fun getSessionStatus(sessionId: String): MutableMap<String?, String?> {
        configureStripe()

        val session = Session.retrieve(sessionId)
        val map: MutableMap<String?, String?> = HashMap<String?, String?>()

        map.put("status", session.getRawJsonObject().getAsJsonPrimitive("status").getAsString())
        map.put("payment_status", session.getRawJsonObject().getAsJsonPrimitive("payment_status").getAsString())
        map.put(
            "customer_email",
            session.getRawJsonObject().getAsJsonObject("customer_details").getAsJsonPrimitive("email").getAsString()
        )

        return map
    }

    private fun configureStripe() {
        val secretKey = environment.getProperty("stripe.secret-key")
            ?: throw IllegalStateException("Missing STRIPE_SECRET_KEY")
        Stripe.apiKey = secretKey
    }

}
