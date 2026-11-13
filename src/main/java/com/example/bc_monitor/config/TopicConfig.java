package com.example.bc_monitor.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfig {


    @Bean
    public NewTopic topicosPrecos (@Value("${btc.topicos.precos}") String nome){
        return TopicBuilder.name(nome)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
