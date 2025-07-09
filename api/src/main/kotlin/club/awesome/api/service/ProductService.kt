package club.awesome.api.service

import club.awesome.api.domain.Member
import club.awesome.api.domain.MemberProduct
import club.awesome.api.domain.Product
import club.awesome.api.domain.ProductStatus
import club.awesome.api.dto.ProductDto
import club.awesome.api.repo.MemberProductRepo
import club.awesome.api.repo.ProductRepo
import club.awesome.api.resource.exception.NotFoundException

import org.slf4j.LoggerFactory
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import java.util.*
import java.util.UUID


@Service
class ProductService(
  private val utils: Utils,
  private val productRepo: ProductRepo,
  private val memberProductRepo: MemberProductRepo,
) {
  private val log = LoggerFactory.getLogger(ProductService::class.java)

  fun createProduct(author: Member): Product {
    val product = Product(
      id = UUID.randomUUID().toString(),
      status = ProductStatus.DRAFT,
      author = author
    )
    return productRepo.save(product)
  }

  fun update(product: Product, dto: ProductDto) {
    product.name = dto.name

    product.url = utils.toUrl(dto.url)
    product.price = dto.price
    product.description = dto.description
    product.data = dto.data
    product.hero = dto.hero
    log.info("=> ${dto.price}")

    productRepo.save(product)
  }

  fun getOwnerProduct(ownerId: String, productId: String): MemberProduct? {
    return memberProductRepo.findOneByOwnerIdAndProductIdWidthProduct(ownerId, productId)
  }

  fun buyProduct(productId: String, ownerId: String): MemberProduct {
    val product = productRepo.findOneById(productId) ?: throw NotFoundException("product.not.found")

    return memberProductRepo.save(MemberProduct(
      id = UUID.randomUUID().toString(),
      ownerId = ownerId,
      price = product.price,
      product = product,
      purchaseDate = Date()
    ))
  }

  fun delete(product: Product) {
    product.removedDate = Date()
    product.removed = true
    productRepo.save(product)
  }
}
