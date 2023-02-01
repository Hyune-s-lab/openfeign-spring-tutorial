package com.example.openfeignspringtutorial.controller

import com.example.openfeignspringtutorial.openfeign.FakeApiOpenFeign
import com.example.openfeignspringtutorial.openfeign.response.PostResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class PostController(private val fakeApiOpenFeign: FakeApiOpenFeign) {
    @GetMapping("/posts/{postId}")
    fun getPost(@PathVariable postId: Long): PostResponse = fakeApiOpenFeign.getPost(postId)
}
