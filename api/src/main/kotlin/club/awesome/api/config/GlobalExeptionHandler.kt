package club.awesome.api.config

import club.awesome.api.resource.exception.ValidationException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@ControllerAdvice
class GlobalExceptionHandler {
  private val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

  @ExceptionHandler(ValidationException::class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  fun handleValidationException(ex: ValidationException): Map<String, Any> {
    log.error(ex.message, ex)
    return mapOf(
      "message" to (ex.message ?: "validation.error"),
      "status" to HttpStatus.BAD_REQUEST.value()
    )
  }

  @ExceptionHandler(Exception::class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  fun handleGenericException(ex: Exception): Map<String, Any> {
    log.error(ex.message, ex)
    return mapOf(
      "message" to "internal.server.error",
      "status" to HttpStatus.INTERNAL_SERVER_ERROR.value()
    )
  }
}
