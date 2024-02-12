package com.redpond.sampleapp.util

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDateTime

class LocalDateFormatterKtTest {

    @Test
    fun parseToYyyyMMddHHmm() {
        val date = "2021-01-01 00:00:00"
        val result = date.parseToYyyyMMddHHmm()
        assertEquals("2021-01-01T00:00", result.toString())
    }

    @Test
    fun parseToYyyyMMddHHmm_invalid() {
        val date = "2021/01/01 00:00"
        val result = runCatching { date.parseToYyyyMMddHHmm() }
        assertTrue(result.isFailure)
    }

    @Test
    fun formatToJpDateTime() {
        val localDateTime = LocalDateTime.of(2021, 10, 10, 10, 10)
        val result = localDateTime.formatToJpDateTime()
        assertEquals("10月10日 10:10", result)
    }

    @Test
    fun formatToJpDateTime_withZero() {
        val localDateTime = LocalDateTime.of(2021, 1, 1, 1, 1)
        val result = localDateTime.formatToJpDateTime()
        assertEquals("1月1日 01:01", result)
    }
}