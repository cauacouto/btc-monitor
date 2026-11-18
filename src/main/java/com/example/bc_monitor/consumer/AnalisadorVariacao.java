package com.example.bc_monitor.consumer;

import com.example.bc_monitor.dto.PrecoColetado;
import com.example.bc_monitor.dto.VariacaoCalculada;
import com.example.bc_monitor.enums.Movimento;
import com.example.bc_monitor.producer.VariacaoProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class AnalisadorVariacao {

    private final Map<String, BigDecimal> ultimoPreco = new HashMap<>();
    private final BigDecimal limiarEstavel;
    private final VariacaoProducer variacaoProducer;

    public AnalisadorVariacao(@Value("${btc.analise.limiar-estavel}") BigDecimal limiarEstavel, VariacaoProducer variacaoProducer) {
        this.limiarEstavel = limiarEstavel;
        this.variacaoProducer = variacaoProducer;
    }

    @KafkaListener(topics = "${btc.topicos.precos}")
    public void analisar(PrecoColetado preco) {

        BigDecimal anterior = ultimoPreco.get(preco.simbolo());
        ultimoPreco.put(preco.simbolo(),preco.preco());

        if (anterior == null){
            log.info("primeira leitura de {}: {}",preco.simbolo(),
                    preco.preco());
            return;

        }

        BigDecimal variacao = calcularVariacao(anterior,preco.preco());
        Movimento movimento = classificar(variacao,limiarEstavel);
        VariacaoCalculada resultado = new VariacaoCalculada(
                preco.simbolo(),     // simbolo
                anterior,            // precoAnterior
                preco.preco(),       // precoAtual
                variacao,            // variacaoPercentual
                movimento,           // movimento
                preco.coletadoEm()   // calculadoEm
        );

        variacaoProducer.publicar(resultado);
    }


    private BigDecimal calcularVariacao(BigDecimal anterior , BigDecimal atual){
        return atual.subtract(anterior)
                .multiply(BigDecimal.valueOf(100))
                .divide(anterior,4, RoundingMode.HALF_UP);
    }

    private Movimento classificar(BigDecimal varicao,BigDecimal limiar){
        if (varicao.abs().compareTo(limiar) < 0){
            return Movimento.ESTAVEL;
        }
        return varicao.signum() > 0 ? Movimento.SUBIU : Movimento.CAIU;
    }
}
