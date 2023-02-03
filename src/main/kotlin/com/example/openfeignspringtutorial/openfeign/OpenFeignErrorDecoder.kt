package com.example.openfeignspringtutorial.openfeign

import feign.Response
import feign.codec.ErrorDecoder
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException

@Component
class OpenFeignErrorDecoder : ErrorDecoder {
    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun decode(methodKey: String, response: Response): Exception {
        log.info("### ${response.status()}, methodKey = $methodKey");

        if (response.status() == HttpStatus.BAD_REQUEST.value()) {
            return ResponseStatusException(
                HttpStatus.valueOf(response.status()),
                "<You can add error message description here>"
            )
        }

        if (response.status() == HttpStatus.NOT_FOUND.value()) {
            return ResponseStatusException(
                HttpStatus.valueOf(response.status()),
                "<You can add error message description here>"
            )
        }

        if (response.status() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            return ResponseStatusException(
                HttpStatus.valueOf(response.status()),
                "<You can add error message description here>"
            )
        }

        return Exception(response.reason())
    }
}
