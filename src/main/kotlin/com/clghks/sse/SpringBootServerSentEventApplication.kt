package com.clghks.sse

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBootServerSentEventApplication

fun main(args: Array<String>) {
	runApplication<SpringBootServerSentEventApplication>(*args)
}
