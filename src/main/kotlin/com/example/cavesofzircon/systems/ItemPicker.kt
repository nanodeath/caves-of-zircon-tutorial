package com.example.cavesofzircon.systems

import com.example.cavesofzircon.commands.PickItemUp
import com.example.cavesofzircon.events.logGameEvent
import com.example.cavesofzircon.extensions.*
import com.example.cavesofzircon.types.Item
import com.example.cavesofzircon.types.addItem
import com.example.cavesofzircon.world.GameContext
import com.example.cavesofzircon.world.World
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.zircon.api.data.Position3D

object ItemPicker : BaseFacet<GameContext, PickItemUp>(PickItemUp::class) {
    override suspend fun receive(message: PickItemUp): Response {
        val world = message.context.world
        val item = world.findTopItem(message.source.position)
        if (item != null && message.source.addItem(item)) {
            val itemHolder: GameItemHolder = message.source
            world.removeEntity(item)
            val subject = if (itemHolder.isPlayer) "You" else "The $itemHolder"
            val verb = if (itemHolder.isPlayer) "pick up" else "picks up"
            logGameEvent("$subject $verb the $item", subject)
        }
        return Consumed
    }
}

fun World.findTopItem(position: Position3D): GameEntity<Item>? {
    return fetchBlockAtOrNull(position)?.entities?.filterType<Item>()?.firstOrNull()
}
