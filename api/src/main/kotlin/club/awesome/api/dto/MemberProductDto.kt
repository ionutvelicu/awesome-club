package club.awesome.api.dto

data class MemberProductDto(
  val purchaseId: String = "",
  val progress: Double = 0.0,
  val complete: Boolean = false,
  var product: ProductDto = ProductDto()
)
