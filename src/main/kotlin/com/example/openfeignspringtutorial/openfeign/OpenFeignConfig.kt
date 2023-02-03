package com.example.openfeignspringtutorial.openfeign

import feign.Retryer
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients("com.example.openfeignspringtutorial.openfeign")
class OpenFeignConfig {
    @Bean
    fun retryer(): Retryer {
        return Retryer.Default(300, 2000, 3)
    }
}
