package com.example.openfeignspringtutorial.openfeign

import com.example.openfeignspringtutorial.openfeign.response.PostResponse
import com.example.openfeignspringtutorial.testconfig.FeignSuccessStub
import com.example.openfeignspringtutorial.testconfig.WireMockConfig
import com.github.tomakehurst.wiremock.WireMockServer
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.io.FileReader

@ActiveProfiles("test")
@SpringBootTest(classes = [WireMockConfig::class])
internal class BooksClientIntegrationTest(
    private val wireMockServer: WireMockServer,
    private val fakeApiOpenFeign: FakeApiOpenFeign,
) : BehaviorSpec({
    Given("getPosts stub - success") {
        val getPostsWireMockResponsePath = "payload/post.json"
        FeignSuccessStub.getPosts(wireMockServer, getPostsWireMockResponsePath)

        When("feign 으로 getPost 호출") {
            val response = fakeApiOpenFeign.getPost(1L)

            Then("response 일치") {
                val wireMockResponsePath = "src/test/resources/__files/"
                val expected: PostResponse = Gson().fromJson(
                    JsonReader(FileReader(wireMockResponsePath + getPostsWireMockResponsePath)),
                    PostResponse::class.java
                )
                response shouldBe expected
            }
        }
    }
}) {
    override suspend fun beforeSpec(spec: Spec) {
        super.beforeSpec(spec)
        wireMockServer.start()
    }

    override suspend fun afterSpec(spec: Spec) {
        super.afterSpec(spec)
        wireMockServer.stop()
    }
}
