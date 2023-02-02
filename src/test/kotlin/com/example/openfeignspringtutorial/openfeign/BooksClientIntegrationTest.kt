package com.example.openfeignspringtutorial.openfeign

import com.example.openfeignspringtutorial.testconfig.FeignSuccessStub
import com.example.openfeignspringtutorial.testconfig.WireMockConfig
import com.github.tomakehurst.wiremock.WireMockServer
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.FunSpec
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest(classes = [WireMockConfig::class])
internal class BooksClientIntegrationTest(
    private val wireMockServer: WireMockServer,
    private val fakeApiOpenFeign: FakeApiOpenFeign,
) : FunSpec({
    test("success stub - getPosts") {
        FeignSuccessStub.getPosts(wireMockServer)
        fakeApiOpenFeign.getPost(1L)
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
