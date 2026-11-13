package com.example.bc_monitor.scheduler;

import com.example.bc_monitor.service.BtcService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    private final BtcService btcService;

    public Scheduler(BtcService btcService) {
        this.btcService = btcService;
    }

    @Scheduled(fixedDelayString = "${btc.coleta.intervalo}")
    public void executar(){

        btcService.coletar();
    }
}
