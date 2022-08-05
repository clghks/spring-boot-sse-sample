package com.clghks.sse.model

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

data class EmitterModel(
    val userId: Long,
    val emitter: SseEmitter
)

data class EmitterEventModel(
    val userId: Long,
    val timestamp: Long,
    val event: NotificationRequest
)