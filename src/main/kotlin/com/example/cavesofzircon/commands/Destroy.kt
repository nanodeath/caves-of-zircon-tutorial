package com.example.cavesofzircon.commands

import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.messages.EntityAction
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.entity.EntityType

data class Destroy(
    override val context: GameContext,
    override val source: GameEntity<EntityType>,
    override val target: GameEntity<EntityType>,
    val cause: String = "natural causes."
) : EntityAction<EntityType, EntityType>
