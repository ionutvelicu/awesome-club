package club.awesome.api.service

import org.springframework.stereotype.Component

@Component
class Validators {
  fun isValidPassword(pass: String) = pass.length >= 7
}
