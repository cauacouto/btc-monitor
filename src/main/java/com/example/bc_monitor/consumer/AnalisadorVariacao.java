package com.example.bc_monitor.consumer;

import com.example.bc_monitor.dto.PrecoColetado;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AnalisadorVariacao {

    @KafkaListener(topics = "${btc.topicos.precos}")
    public void analisar(PrecoColetado preco) {
        log.info("recebido: {}", preco);
    }
}
