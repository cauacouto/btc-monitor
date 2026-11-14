package com.example.bc_monitor.producer;

import com.example.bc_monitor.dto.PrecoColetado;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PrecoProducer {

    private final KafkaTemplate<String, PrecoColetado> kafkaTemplate;
    private final String topico;

    public PrecoProducer(KafkaTemplate<String, PrecoColetado> kafkaTemplate, @Value("${btc.topicos.precos}")String topico) {
        this.kafkaTemplate = kafkaTemplate;
        this.topico = topico;
    }

    public void publicar(PrecoColetado preco){
        kafkaTemplate.send(topico,preco.simbolo(),preco)
                .whenComplete((resultado,erro) ->{
                    if (erro == null){
                        log.info("publicado {} na partição {} offset {}",preco.simbolo(),resultado.getRecordMetadata().partition()
                                ,resultado.getRecordMetadata().offset());
                    }else {
                        log.error("falha ao publicar {}",preco.simbolo(),erro);
                    }
                });


    }

}
