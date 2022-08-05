package com.clghks.sse.controller

import com.clghks.sse.model.NotificationRequest
import com.clghks.sse.service.NotificationService
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@RestController
class NotificationController(
    private val notificationService: NotificationService
) {
    @GetMapping("/subscribe/{id}", produces = ["text/event-stream"])
    fun subscribe(@PathVariable id: Long, @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") lastEventId: String): SseEmitter? {
        return notificationService.subscribe(id, lastEventId)
    }

    @PostMapping("/notification/{id}")
    fun createNotification(@PathVariable id: Long, @RequestBody request: NotificationRequest) {
        notificationService.createNotification(id, request)
    }
}

