package com.example.bc_monitor.config;

import com.example.bc_monitor.dto.PrecoColetado;
import com.example.bc_monitor.dto.VariacaoCalculada;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProducerKafkaConfig {

    @Bean
    public ProducerFactory<String, PrecoColetado> producerFactory(@Value("${spring.kafka.bootstrap-servers}") String bootstrapAddress) {
        return new DefaultKafkaProducerFactory<>(configsComuns(bootstrapAddress));
    }

    @Bean
    public KafkaTemplate<String, PrecoColetado> kafkaTemplate(
            ProducerFactory<String, PrecoColetado> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public ProducerFactory<String, VariacaoCalculada> producerFactoryVariacao(@Value("${spring.kafka.bootstrap-servers}") String bootstrapAddress) {
        return new DefaultKafkaProducerFactory<>(configsComuns(bootstrapAddress));
    }

    @Bean
    public KafkaTemplate<String, VariacaoCalculada> kafkaTemplateVariacao(
            ProducerFactory<String, VariacaoCalculada> producerFactoryVariacao) {
        return new KafkaTemplate<>(producerFactoryVariacao);
    }

    private Map<String, Object> configsComuns(String bootstrapAddress) {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JacksonJsonSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        return props;
    }
}
