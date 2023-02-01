package com.example.openfeignspringtutorial.openfeign

import com.example.openfeignspringtutorial.openfeign.response.PostResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.*

@FeignClient(name = "fake-api", url = "https://jsonplaceholder.typicode.com")
interface FakeApiOpenFeign {
    @GetMapping("/posts/{postId}")
    fun getPost(@PathVariable postId: Long): PostResponse
}
