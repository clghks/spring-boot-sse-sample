package com.clghks.sse.repository

import com.clghks.sse.model.EmitterEventModel
import com.clghks.sse.model.EmitterModel
import com.clghks.sse.model.NotificationRequest
import org.springframework.stereotype.Repository
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter


@Repository
class EmitterRepository {
    val emitters: MutableList<EmitterModel> = arrayListOf()
    val eventCache: MutableList<EmitterEventModel> = arrayListOf()

    fun save(userId: Long, sseEmitter: SseEmitter): SseEmitter? {
        emitters.add(EmitterModel(userId, sseEmitter))
        return sseEmitter
    }

    fun saveEventCache(userId: Long, timestamp: Long, event: NotificationRequest) {
        eventCache.add(EmitterEventModel(userId, timestamp, event))
    }

    fun findByUserId(userId: Long): SseEmitter? {
        return emitters.find { it.userId == userId }?.emitter
    }

    fun deleteById(userId: Long) {
        emitters.removeAll {
            it.userId == userId
        }
    }

    fun findEventCacheId(userId: Long, lastEventId: Long): List<EmitterEventModel> {
        return eventCache.filter { it.userId == userId }.filter { it.timestamp > lastEventId }.toList()
    }
}