package com.penguin.esms.components.warrantyProduct;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WarrantyProductConfig {
    @Bean
    CommandLineRunner commandLineRunnerWarrantyProduct(WarrantyProductRepo warrantyProductRepo){
        return args -> {};
    }

}
