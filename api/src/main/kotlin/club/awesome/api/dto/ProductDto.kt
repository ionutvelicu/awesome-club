package club.awesome.api.dto

data class ProductDto(
  val id: String = "",
  val name: String = "",
  val description: String = "",
  val url: String = "",
  val hero: String = "",
  val price: Double = 0.0,
  val status: String = "",
  val data: String = "",
  val authorId: String = "",
  val authorName: String = "",
)
