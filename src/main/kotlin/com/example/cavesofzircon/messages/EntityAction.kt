package com.example.cavesofzircon.messages

import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.extensions.GameMessage
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.entity.EntityType

interface EntityAction<out S: EntityType, out T: EntityType> : GameMessage {
    val target: GameEntity<T>

    operator fun component1() = context
    operator fun component2() = source
    operator fun component3() = target
}

interface EntityActionBuilder<S: EntityType, T: EntityType> {
    fun create(
        context: GameContext,
        source: GameEntity<S>,
        target: GameEntity<T>
    ) : EntityAction<S, T>
}