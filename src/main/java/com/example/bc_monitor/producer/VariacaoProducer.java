package com.example.bc_monitor.producer;

import com.example.bc_monitor.dto.VariacaoCalculada;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class VariacaoProducer {

    private final KafkaTemplate<String, VariacaoCalculada> kafkaTemplate;
    private final String topico;

    public VariacaoProducer(KafkaTemplate<String, VariacaoCalculada> kafkaTemplate,
                            @Value("${btc.topicos.variacoes}") String topico) {
        this.kafkaTemplate = kafkaTemplate;
        this.topico = topico;
    }

    public void publicar(VariacaoCalculada variacao) {
        kafkaTemplate.send(topico, variacao.simbolo(), variacao)
                .whenComplete((resultado, erro) -> {
                    if (erro == null) {
                        log.info("variação {} {} publicada na partição {} offset {}",
                                variacao.simbolo(),
                                variacao.movimento(),
                                resultado.getRecordMetadata().partition(),
                                resultado.getRecordMetadata().offset());
                    } else {
                        log.error("falha ao publicar variação de {}", variacao.simbolo(), erro);
                    }
                });
    }
}
