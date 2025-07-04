package club.awesome.api.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.*

@Component
class Dates {
  private val log: Logger = LoggerFactory.getLogger(Dates::class.java)

  companion object {
    const val datePattern = "yyyy-MM-dd"
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
    const val defPattern = "yyyy-MM-dd HH:mm"
    val defFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(defPattern)
  }

  fun now() = Date(System.currentTimeMillis())

  fun date(): LocalDate = LocalDate.now()

  fun dateTime(): LocalDateTime = LocalDateTime.now()

  fun format(date: LocalDateTime, pattern: String = defPattern): String = if (defPattern == pattern) {
    defFormatter.format(date)
  } else {
    DateTimeFormatter.ofPattern(pattern).format(date)
  }

  fun format(date: Date, pattern: String = defPattern) = format(toLocalDateTime(date), pattern)

  fun toLocalDateTime(date: Date): LocalDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()

  fun plusHours(hours: Long): Date {
    val systemZone = ZoneId.systemDefault()
    val offset = systemZone.rules.getOffset(Instant.now())
    return Date.from(dateTime().plusHours(hours).toInstant(offset))
  }

  fun plusDays(days: Long): Date {
    val systemZone = ZoneId.systemDefault()
    val offset = systemZone.rules.getOffset(Instant.now())
    return Date.from(dateTime().plusDays(days).toInstant(offset))
  }

  fun minusHours(hours: Long): Date = plusHours(-hours)

  fun startOfDay(): Date = Date.from(date().atStartOfDay(ZoneId.systemDefault()).toInstant())

  fun startOfMonth(add: Long = 0): Date {
    val month = date().with(TemporalAdjusters.firstDayOfMonth()).plusMonths(add)
    return Date.from(month.atStartOfDay(ZoneId.systemDefault()).toInstant())
  }

  fun endOfMonth(add: Long = 0): Date {
    val month = date().with(TemporalAdjusters.lastDayOfMonth()).plusMonths(add).plusDays(1)
    return Date.from(month.atStartOfDay(ZoneId.systemDefault()).toInstant())
  }

  fun startOfYear(add: Long = 0): Date {
    val month = date().with(TemporalAdjusters.firstDayOfYear()).plusYears(add)
    return Date.from(month.atStartOfDay(ZoneId.systemDefault()).toInstant())
  }

  fun endOfYear(add: Long = 0): Date {
    val month = date().with(TemporalAdjusters.lastDayOfYear()).plusYears(add).plusDays(1)
    return Date.from(month.atStartOfDay(ZoneId.systemDefault()).toInstant())
  }

  fun parseDate(value: String): Date = dateFormatter.parse(value)

  fun getDate(day: String, time: String): Date {
    val calendar = Calendar.getInstance()
    calendar.time = parseDate(day)

    val bits = time.split(":")
    calendar.set(Calendar.HOUR_OF_DAY, bits[0].toInt())
    calendar.set(Calendar.MINUTE, bits[1].toInt())

    return calendar.time
  }
}
