package com.example.cavesofzircon.commands

import com.example.cavesofzircon.extensions.AnyGameEntity
import com.example.cavesofzircon.messages.EntityAction
import com.example.cavesofzircon.messages.EntityActionBuilder
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.entity.EntityType

data class Dig(
    override val context: GameContext,
    override val source: AnyGameEntity,
    override val target: AnyGameEntity
) : EntityAction<EntityType, EntityType> {
    companion object : EntityActionBuilder<EntityType, EntityType> {
        override fun create(
            context: GameContext,
            source: AnyGameEntity,
            target: AnyGameEntity?
        ) = Dig(context, source, target!!)
    }
}
