package club.awesome.api.config.auth

import club.awesome.api.service.AuthService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
  @Qualifier("customUserDetailsService")
  val userDetailsService: UserDetailsService,
  val authService: AuthService,
  val config: JwtConfig,
  val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint
) {

  @Bean
  fun passwordEncoder() = BCryptPasswordEncoder()

  @Bean
  fun authenticationManager(http: HttpSecurity): AuthenticationManager {
    val builder = http.getSharedObject(AuthenticationManagerBuilder::class.java)
    builder
      .userDetailsService(userDetailsService)
      .passwordEncoder(passwordEncoder())
    return builder.build()
  }

  @Bean
  fun filterChain(http: HttpSecurity): SecurityFilterChain {
    http
      .exceptionHandling {
        it.authenticationEntryPoint(jwtAuthenticationEntryPoint)
      }
      .csrf { it.disable() }
      .cors { }
      .sessionManagement {
        it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      }
      .authorizeHttpRequests {
        it
          .requestMatchers("/public/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
          .anyRequest().authenticated()
      }
      .addFilterBefore(JwtAuthorizationFilter(userDetailsService, authService, config), UsernamePasswordAuthenticationFilter::class.java)

    return http.build()
  }
}
