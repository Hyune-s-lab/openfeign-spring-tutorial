package com.example.openfeignspringtutorial.testconfig

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

object FeignSuccessStub {
    fun getPosts(mockService: WireMockServer, wireMockResponsePath: String) {
        mockService.stubFor(
            WireMock.get(WireMock.urlPathMatching("/posts/([1-99])"))
                .willReturn(
                    WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile(wireMockResponsePath)
                )
        )
    }
}
