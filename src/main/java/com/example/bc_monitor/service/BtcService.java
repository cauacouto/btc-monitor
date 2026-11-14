package com.example.bc_monitor.service;

import com.example.bc_monitor.client.BinanceClient;
import com.example.bc_monitor.dto.BinanceTicker24h;
import com.example.bc_monitor.dto.PrecoColetado;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;

@Service
@Slf4j
public class BtcService {

    private final BinanceClient binanceClient;
    private final Clock clock;
    private final String simbolo;


    public BtcService(BinanceClient binanceClient, Clock clock, @Value("${btc.coleta.simbolo}") String simbolo) {
        this.binanceClient = binanceClient;
        this.clock = clock;
        this.simbolo = simbolo;

    }

    public void coletar() {
        try {
            BinanceTicker24h ticker = binanceClient.buscarTicker(simbolo);
            if (ticker == null){
                log.warn("binance respondeu sem corpo para {}",simbolo);
                return;
            }

            PrecoColetado preco = new PrecoColetado(

                    ticker.symbol(),
                    new BigDecimal(ticker.lastPrice()),
                    new BigDecimal(ticker.priceChangePercent()),
                    Instant.now(clock)

            );
            if (preco.preco().compareTo(BigDecimal.ZERO) <= 0){
                log.warn("preço invalido para {}: {}", simbolo,preco.preco());
                return;
            }

            log.info("preço coletado: {}", preco);
        } catch (RestClientException e) {
            log.warn("falha ao coletar {}: {}", simbolo,
                    e.getMessage());

        }catch (NumberFormatException e){
            log.error("formato inesperado da binance para {}",simbolo,e);
        }
    }
}
