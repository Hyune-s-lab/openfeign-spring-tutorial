package com.example.openfeignspringtutorial.openfeign

import com.example.openfeignspringtutorial.openfeign.response.PostResponse
import com.example.openfeignspringtutorial.testconfig.WireMockServerConfig
import com.example.openfeignspringtutorial.testconfig.WireMockServerConst
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.server.ResponseStatusException
import java.io.FileReader

@ActiveProfiles("test")
@SpringBootTest(classes = [WireMockServerConfig::class])
internal class FakeApiOpenFeignTest(
    private val wireMockServer: WireMockServer,
    private val fakeApiOpenFeign: FakeApiOpenFeign,
) : BehaviorSpec({
    Given("getPosts stub - success") {
        wireMockServer.stubFor(
            WireMock.get(WireMock.urlPathMatching(WireMockServerConst.GetPosts.url))
                .willReturn(
                    WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBodyFile(WireMockServerConst.GetPosts.responsePath)
                )
        )

        When("feign 으로 getPost 호출") {
            val response = fakeApiOpenFeign.getPost(1L)

            Then("response 일치") {
                val expected: PostResponse = Gson().fromJson(
                    JsonReader(FileReader(WireMockServerConst.GetPosts.absoluteResponsePath())),
                    PostResponse::class.java
                )
                response shouldBe expected
            }
        }
    }

    Given("getPosts stub - bad request") {
        wireMockServer.stubFor(
            WireMock.get(WireMock.urlPathMatching(WireMockServerConst.GetPosts.url))
                .willReturn(
                    WireMock.badRequest()
                )
        )

        When("feign 으로 getPost 호출") {
            val exception = shouldThrow<ResponseStatusException> {
                fakeApiOpenFeign.getPost(1L)
            }

            Then("bad request 확인") {
                exception.status shouldBe HttpStatus.BAD_REQUEST
            }
        }
    }

    Given("getPosts stub - INTERNAL_SERVER_ERROR") {
        wireMockServer.stubFor(
            WireMock.get(WireMock.urlPathMatching(WireMockServerConst.GetPosts.url))
                .willReturn(
                    WireMock.serverError()
                )
        )

        When("feign 으로 getPost 호출") {
            val exception = shouldThrow<ResponseStatusException> {
                fakeApiOpenFeign.getPost(1L)
            }

            Then("status 확인") {
                exception.status shouldBe HttpStatus.INTERNAL_SERVER_ERROR
            }
        }
    }
})
