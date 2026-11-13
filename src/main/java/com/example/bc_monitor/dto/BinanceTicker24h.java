package com.example.bc_monitor.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public record BinanceTicker24h(

        String symbol,
        String lastPrice,
        String priceChangePercent
) {
}
