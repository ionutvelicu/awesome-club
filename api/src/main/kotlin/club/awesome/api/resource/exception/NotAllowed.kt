package club.awesome.api.resource.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.FORBIDDEN)
class NotAllowed(message: String = "") : RuntimeException(message)
