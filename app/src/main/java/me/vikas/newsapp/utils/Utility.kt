package me.vikas.newsapp.utils

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

object Utility {
    fun calculateInitialDelay(): Long {
        val now = LocalDateTime.now()
        var target = now.withHour(6).withMinute(0).withSecond(0).withNano(0)
        if (now >= target) {
            target = target.plusDays(1)
        }
        return ChronoUnit.MILLIS.between(
            now, target
        )
    }
}