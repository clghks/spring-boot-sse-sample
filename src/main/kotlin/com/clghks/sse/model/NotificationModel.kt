package com.clghks.sse.model

data class NotificationRequest(
    val title: String,
    val message: String,
    val url: String
)