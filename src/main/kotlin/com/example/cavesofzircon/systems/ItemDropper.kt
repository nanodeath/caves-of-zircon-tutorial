package com.example.cavesofzircon.systems

import com.example.cavesofzircon.commands.DropItem
import com.example.cavesofzircon.events.logGameEvent
import com.example.cavesofzircon.extensions.isPlayer
import com.example.cavesofzircon.extensions.position
import com.example.cavesofzircon.types.removeItem
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet

object ItemDropper : BaseFacet<GameContext, DropItem>(DropItem::class) {
    override suspend fun receive(message: DropItem): Response {
        val (context, itemHolder, item) = message
        if (itemHolder.removeItem(item)) {
            context.world.addEntity(item, itemHolder.position)
            val subject = if (itemHolder.isPlayer) "You" else "The $itemHolder"
            val verb = if (itemHolder.isPlayer) "drop" else "drops"
            logGameEvent("$subject $verb the $item.", itemHolder)
        }
        return Consumed
    }
}
