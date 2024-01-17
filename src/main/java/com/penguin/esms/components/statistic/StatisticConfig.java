package com.penguin.esms.components.statistic;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
@EnableAsync
public class StatisticConfig {
    private final StatisticService service;

    @Bean
    CommandLineRunner commandLineRunnerStatistic(StatisticRepository repo) {
        return args -> {
        };
    }

}
