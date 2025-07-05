package club.awesome.api.dto

data class MemberProductLightDto(
  val purchaseId: String = "",
  val progress: Double = 0.0,
  val complete: Boolean = false,
  var name: String = ""
)
