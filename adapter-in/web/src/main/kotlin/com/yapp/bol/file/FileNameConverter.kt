package com.yapp.bol.file

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class FileNameConverter {
    @Value("\${bol.server.host}")
    fun setHost(host: String) {
        FileNameConverter.host = host
    }

    companion object {
        private lateinit var host: String

        fun convertFileUrl(name: String): String = "http://$host/v1/file/$name"
    }
}
