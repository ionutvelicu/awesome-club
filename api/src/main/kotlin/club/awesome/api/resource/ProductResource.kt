package club.awesome.api.resource

import club.awesome.api.domain.Product
import club.awesome.api.dto.*
import club.awesome.api.repo.MemberProductRepo
import club.awesome.api.repo.MemberRepo
import club.awesome.api.repo.ProductRepo
import club.awesome.api.resource.exception.NotAllowed
import club.awesome.api.resource.exception.NotFoundException
import club.awesome.api.service.ProductService
import club.awesome.api.service.Utils
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductResource (
  private val utils: Utils,
  private val memberRepo: MemberRepo,
  private val productRepo: ProductRepo,
  private val memberProductRepo: MemberProductRepo,
  private val productService: ProductService
) {
  private val log = LoggerFactory.getLogger(ProductResource::class.java)

  private fun getProductWithPermission(productId: String): Product {
    val loggedId = utils.loggedId() ?: throw NotAllowed("not.allowed")
    val product = productRepo.findOneWithAuthorById(productId) ?: throw NotFoundException("product.not.found")
    if (product.author?.id != loggedId) throw NotFoundException("product.not.found")
    return product
  }

  @GetMapping("/products")
  fun getProductsForAuthor(): List<ProductDto> {
    val loggedId = utils.loggedId() ?: throw NotAllowed("product.not.allowed")
    return productRepo.findAllByMemberId(loggedId).map { it.toDto() }
  }

  @GetMapping("/products/purchased")
  fun getPurchasedProducts(): List<MemberProductLightDto> {
    val loggedId = utils.loggedId() ?: throw NotAllowed(".not.allowed")
    return memberProductRepo.findByOwnerId(loggedId).map { it.toLightDto() }
  }

  @GetMapping("/products/purchased/{purchasedId}")
  fun getPurchasedProductDetails(@PathVariable purchasedId: String): MemberProductDto {
    val loggedId = utils.loggedId() ?: throw NotAllowed("not.allowed")
    val purchase = memberProductRepo.findOneByIdWithProduct(purchasedId) ?: throw NotFoundException("not.found")
    if (purchase.ownerId != loggedId) throw NotAllowed("not.allowed")

    return purchase.toDto()
  }

  @GetMapping("/products/{id}")
  fun getProduct(@PathVariable id: String): ProductDto {
    val product =  productRepo.findOneById(id) ?: throw NotFoundException("product.not.found")
    return product.toDto()
  }

  @GetMapping("/products/{id}/status")
  fun checkProductStatus(@PathVariable id: String): MemberProductStatusDto {
    val loggedId = utils.loggedId() ?: throw NotAllowed("not.allowed")
    val product = productService.getOwnerProduct(loggedId, id)
    return MemberProductStatusDto(
      purchased = product != null
    )
  }

  @PostMapping("/products/{id}/buy")
  fun buyProduct(@PathVariable id: String): BuyProductResponse {
    val loggedId = utils.loggedId() ?: throw NotAllowed("not.allowed")
    val purchase = productService.buyProduct(id, loggedId)
    return BuyProductResponse(
      purchaseId = purchase.id
    )
  }

  @PostMapping("/products")
  fun createProduct(): ProductDto {
    val loggedId = utils.loggedId() ?: throw NotAllowed("not.allowed")
    val author = memberRepo.findOneById(loggedId) ?: throw NotAllowed("not.allowed")

    return productService
      .createProduct(author)
      .toDto()
  }

  @PostMapping("/products/{id}")
  fun updateProduct(@PathVariable id: String, @RequestBody body: ProductDto) {
    val product = getProductWithPermission(id)
    productService.update(product, body)
  }

  @DeleteMapping("/products/{id}")
  fun deleteProduct(@PathVariable id: String) {
    val product = getProductWithPermission(id)
    productService.delete(product)
  }

  @PostMapping("/products/{productId}/section/{sectionId}/content")
  fun uploadProductSectionContent(@PathVariable productId: String, @PathVariable sectionId: String): String {
    log.info("=> uploadProductSectionContent $productId - $sectionId")
    return "a"
  }
}
