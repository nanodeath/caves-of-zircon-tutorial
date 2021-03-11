package com.example.cavesofzircon.systems

import com.example.cavesofzircon.commands.Destroy
import com.example.cavesofzircon.commands.EntityDestroyed
import com.example.cavesofzircon.events.PlayerDied
import com.example.cavesofzircon.events.logGameEvent
import com.example.cavesofzircon.extensions.isPlayer
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.zircon.internal.Zircon

object Destructible : BaseFacet<GameContext, Destroy>(Destroy::class) {
    override suspend fun receive(message: Destroy): Response {
        val (context, destroyer, target, cause) = message
        context.world.removeEntity(target)
        destroyer.receiveMessage(EntityDestroyed(context, target, destroyer))
        logGameEvent("$target dies $cause", this)
        if (target.isPlayer) {
            Zircon.eventBus.publish(PlayerDied("You died $cause", target))
        }

        return Consumed
    }
}
