package com.example.bc_monitor.dto;

import com.example.bc_monitor.enums.Movimento;

import java.math.BigDecimal;
import java.time.Instant;

public record VariacaoCalculada (
        String simbolo,
        BigDecimal precoAnterior,
        BigDecimal precoAtual,
        BigDecimal variacaoPercentual,
        Movimento movimento,
        Instant calculadoEm

)
{}
