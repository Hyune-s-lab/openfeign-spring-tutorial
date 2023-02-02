package com.example.openfeignspringtutorial.testconfig

import com.github.tomakehurst.wiremock.WireMockServer
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class WireMockConfig {
    @Bean
    fun wireMockServer(): WireMockServer =
        WireMockServer(9561)
}
