package com.plcoding.stockmarketapp.data.mapper

import com.plcoding.stockmarketapp.data.remote.dto.IntradayInfoDto
import com.plcoding.stockmarketapp.domain.model.IntradayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun IntradayInfoDto.toIntradayInfo(): IntradayInfo{
    val pattern="yyyy-MM-dd HH:mm:ss"
    val formatter=DateTimeFormatter.ofPattern(pattern)
    val localDateTime=LocalDateTime.parse(timestamp,formatter)

    return IntradayInfo(
        date = localDateTime,
        close = close
    )
}
