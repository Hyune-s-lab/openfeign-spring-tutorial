package com.example.openfeignspringtutorial.testconfig

object WireMockServerConst {
    const val port = 9561
    const val rootResponsePath = "src/test/resources/__files/"

    object GetPosts {
        const val url = "/posts/([1-99])"
        const val responsePath = "payload/post.json"

        fun absoluteResponsePath(): String = rootResponsePath + responsePath
    }
}
