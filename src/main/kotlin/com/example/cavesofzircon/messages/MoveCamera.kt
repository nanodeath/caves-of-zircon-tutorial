package com.example.cavesofzircon.messages

import com.example.cavesofzircon.extensions.GameMessage
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.zircon.api.data.Position3D

data class MoveCamera(
    override val context: GameContext,
    override val source: Entity<EntityType, GameContext>,
    val previousPosition: Position3D
) : GameMessage
