package com.example.cavesofzircon.commands

import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.messages.EntityAction
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType

data class EntityDestroyed(
    override val context: GameContext,
    /** Destroyed */
    override val source: Entity<EntityType, GameContext>,
    /** Destroyer */
    override val target: GameEntity<EntityType>
) : EntityAction<EntityType, EntityType>
