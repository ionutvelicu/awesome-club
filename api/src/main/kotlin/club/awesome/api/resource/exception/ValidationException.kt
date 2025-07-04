package club.awesome.api.resource.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class ValidationException(message: String = "") : RuntimeException(message)
