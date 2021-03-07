package com.example.cavesofzircon.events

import org.hexworks.cobalt.events.api.Event
import org.hexworks.zircon.internal.Zircon

data class GameLogEvent(val text: String, override val emitter: Any) : Event

fun logGameEvent(text: String, emitter: Any) {
    Zircon.eventBus.publish(GameLogEvent(text, emitter))
}