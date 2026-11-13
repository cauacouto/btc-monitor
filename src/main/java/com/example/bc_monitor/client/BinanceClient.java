package com.example.bc_monitor.client;

import com.example.bc_monitor.dto.BinanceTicker24h;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Component

public class BinanceClient {


 private final  RestClient restClient;


    public BinanceClient(RestClient.Builder builder,
                         @Value("${binance.base-url}") String baseUrl){


        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(3));
        factory.setReadTimeout(Duration.ofSeconds(5));

        this.restClient = builder
                .baseUrl(baseUrl)
                .requestFactory(factory)
                .build();
    }

    public BinanceTicker24h buscarTicker(String simbolo){
        return restClient.get()
                .uri("/api/v3/ticker/24hr?symbol={simbolo}",simbolo)
                .retrieve()
                .body(BinanceTicker24h.class);
    }




}
