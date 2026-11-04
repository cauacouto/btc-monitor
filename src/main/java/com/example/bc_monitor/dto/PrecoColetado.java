package com.example.bc_monitor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class PrecoColetado {
    private String simbolo;
    private BigDecimal preco;
    private  BigDecimal Variacao;
    private Instant coletadoEm;
}
