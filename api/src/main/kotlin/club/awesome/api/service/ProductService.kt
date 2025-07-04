package club.awesome.api.service

import club.awesome.api.domain.Member
import club.awesome.api.domain.Product
import club.awesome.api.domain.ProductStatus
import club.awesome.api.dto.ProductDto
import club.awesome.api.repo.ProductRepo
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductService(
  private val utils: Utils,
  private val productRepo: ProductRepo
) {

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
    product.description = dto.description
    product.data = dto.data

    productRepo.save(product)
  }

  fun delete(product: Product) {
    product.removedDate = Date()
    product.removed = true
    productRepo.save(product)
  }
}
