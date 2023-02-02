package com.example.openfeignspringtutorial.testconfig

import com.github.tomakehurst.wiremock.WireMockServer
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class WireMockServerConfig {
    @Bean
    fun wireMockServer(): WireMockServer =
        WireMockServer(WireMockServerConst.port)
}
