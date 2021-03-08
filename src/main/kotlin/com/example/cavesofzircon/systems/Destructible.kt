package com.example.cavesofzircon.systems

import com.example.cavesofzircon.commands.Destroy
import com.example.cavesofzircon.events.logGameEvent
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet

object Destructible : BaseFacet<GameContext, Destroy>(Destroy::class) {
    override suspend fun receive(message: Destroy): Response {
        message.context.world.removeEntity(message.target)
        logGameEvent("${message.target} dies ${message.cause}", this)
        return Consumed
    }
}
