package com.example.openfeignspringtutorial.openfeign

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients("com.example.openfeignspringtutorial.openfeign")
class OpenFeignConfig
