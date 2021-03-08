package com.example.cavesofzircon.systems

import com.example.cavesofzircon.commands.Destroy
import com.example.cavesofzircon.commands.DropItem
import com.example.cavesofzircon.extensions.takeIfType
import com.example.cavesofzircon.types.ItemHolder
import com.example.cavesofzircon.types.inventory
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet

object LootDropper : BaseFacet<GameContext, Destroy>(Destroy::class) {
    override suspend fun receive(message: Destroy): Response {
        val (context, _, target) = message
        target.takeIfType<ItemHolder>()?.let { entity ->
            for (item in entity.inventory.items.toList()) {
                entity.receiveMessage(DropItem(context, entity, item))
            }
        }
        return Pass
    }
}
