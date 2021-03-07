package com.example.cavesofzircon.attributes

import com.example.cavesofzircon.extensions.AnyGameEntity
import com.example.cavesofzircon.messages.EntityAction
import com.example.cavesofzircon.messages.EntityActionBuilder
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.base.BaseAttribute
import org.hexworks.amethyst.api.entity.EntityType

// TODO replace with companion objects w/ interfaces
class EntityActions(
    private vararg val actions: EntityActionBuilder<out EntityType, out EntityType>
) : BaseAttribute() {
    fun createActionsFor(
        context: GameContext,
        source: AnyGameEntity,
        target: AnyGameEntity
    ): Iterable<EntityAction<out EntityType, out EntityType>> {
        return actions.map { action ->
            action.create(context, source, target)
        }
    }
}