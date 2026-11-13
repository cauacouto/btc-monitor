package com.example.bc_monitor.dto;

import java.math.BigDecimal;
import java.time.Instant;


public record PrecoColetado (

     String simbolo,
     BigDecimal preco,
      BigDecimal variacao24h,
     Instant coletadoEm
){}
