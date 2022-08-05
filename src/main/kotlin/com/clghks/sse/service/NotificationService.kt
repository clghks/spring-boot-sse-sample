package com.clghks.sse.service

import com.clghks.sse.model.NotificationRequest
import com.clghks.sse.repository.EmitterRepository
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@Service
class NotificationService(
    val emitterRepository: EmitterRepository
) {
    companion object {
        private const val DEFAULT_TIMEOUT = 60L * 1000 * 60
    }

    fun subscribe(userId: Long, lastEventId: String): SseEmitter? {
        val emitter = emitterRepository.save(userId, SseEmitter(DEFAULT_TIMEOUT))
        emitter?.onCompletion { emitterRepository.deleteById(userId) }
        emitter?.onTimeout { emitterRepository.deleteById(userId) }

        sendToClient(emitter, "${System.currentTimeMillis()}", "EventStream Created. [userId=$userId]")

        if (!lastEventId.isNullOrEmpty()) {
            emitterRepository.findEventCacheId(userId, lastEventId.toLong()).forEach {
                sendToClient(emitter, it.timestamp.toString(), it.event)
            }
        }

        return emitter
    }

    fun sendToClient(emitter: SseEmitter?, id: String, data: Any) {
        emitter?.send(SseEmitter.event().id(id).name("sse").data(data))
    }

    fun createNotification(userId: Long, request: NotificationRequest) {
        val timestamp = System.currentTimeMillis()
        sendToClient(emitterRepository.findByUserId(userId), "$timestamp", request)
        emitterRepository.saveEventCache(userId, timestamp, request)
    }
}