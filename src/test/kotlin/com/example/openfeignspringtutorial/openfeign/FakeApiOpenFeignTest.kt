package com.example.openfeignspringtutorial.openfeign

import com.example.openfeignspringtutorial.openfeign.response.PostResponse
import com.example.openfeignspringtutorial.testconfig.WireMockServerConfig
import com.example.openfeignspringtutorial.testconfig.WireMockServerConst
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import feign.RetryableException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldStartWith
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
        wireMockServer.stubGetPosts(
            WireMock.aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBodyFile(WireMockServerConst.GetPosts.responsePath)
        )

        When("feign 으로 getPost 호출") {
            val then = fakeApiOpenFeign.getPost(1L)

            Then("response 일치") {
                val expected: PostResponse = Gson().fromJson(
                    JsonReader(FileReader(WireMockServerConst.GetPosts.absoluteResponsePath())),
                    PostResponse::class.java
                )
                then shouldBe expected
            }
        }
    }

    Given("getPosts stub - BAD_REQUEST") {
        wireMockServer.stubGetPosts(WireMock.badRequest())

        When("feign 으로 getPost 호출") {
            val then = shouldThrow<ResponseStatusException> {
                fakeApiOpenFeign.getPost(1L)
            }

            Then("BAD_REQUEST 확인") {
                then.status shouldBe HttpStatus.BAD_REQUEST
            }
        }
    }

    Given("getPosts stub - INTERNAL_SERVER_ERROR") {
        wireMockServer.stubGetPosts(WireMock.serverError())

        When("feign 으로 getPost 호출") {
            val then = shouldThrow<ResponseStatusException> {
                fakeApiOpenFeign.getPost(1L)
            }

            Then("INTERNAL_SERVER_ERROR 확인") {
                then.status shouldBe HttpStatus.INTERNAL_SERVER_ERROR
            }
        }
    }

    Given("getPosts stub - Connection refused") {
        wireMockServer.stop()

        When("feign 으로 getPost 호출") {
            val then = shouldThrow<RetryableException> {
                fakeApiOpenFeign.getPost(1L)
            }

            Then("Connection refused 확인") {
                then.message shouldStartWith "Connection refused"
            }
        }
    }
})

private fun WireMockServer.stubGetPosts(responseDefinitionBuilder: ResponseDefinitionBuilder) {
    stubFor(
        WireMock.get(WireMock.urlPathMatching(WireMockServerConst.GetPosts.url))
            .willReturn(responseDefinitionBuilder)
    )
}
