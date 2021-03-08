package com.example.cavesofzircon.attributes

import com.example.cavesofzircon.extensions.AnyGameEntity
import com.example.cavesofzircon.messages.EntityAction
import com.example.cavesofzircon.messages.EntityActionBuilder
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.base.BaseAttribute
import org.hexworks.amethyst.api.entity.Entity

class EntityActions(
    private vararg val actions: EntityActionBuilder<*, *>
) : BaseAttribute() {
    fun createActionsFor(
        context: GameContext,
        source: AnyGameEntity,
        target: AnyGameEntity
    ): Iterable<EntityAction<*, *>> {
        return actions.map { action ->
            // TODO see if I can fix these casts
            action.create(context, source as Entity<Nothing, GameContext>, target as Entity<Nothing, GameContext>?)
        }
    }
}
