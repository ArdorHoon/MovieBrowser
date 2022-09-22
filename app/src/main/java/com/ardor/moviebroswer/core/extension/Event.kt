package com.ardor.moviebroswer.core.extension

open class Event<out T>(private val content: T) {

    var hashBeenHandled = false
        private set //Allow external read but not write

    fun getContentIfNotHandled(): T? {
        return if (hashBeenHandled) {
            null
        } else {
            hashBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content
}

