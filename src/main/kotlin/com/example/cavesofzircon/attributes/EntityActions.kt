package com.example.cavesofzircon.attributes

import com.example.cavesofzircon.extensions.AnyGameEntity
import com.example.cavesofzircon.messages.EntityAction
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.base.BaseAttribute
import org.hexworks.amethyst.api.entity.EntityType
import kotlin.reflect.KClass

// TODO replace with companion objects w/ interfaces
class EntityActions(
    private vararg val actions: KClass<out EntityAction<out EntityType, out EntityType>>
) : BaseAttribute() {
    fun createActionsFor(
        context: GameContext,
        source: AnyGameEntity,
        target: AnyGameEntity
    ): Iterable<EntityAction<out EntityType, out EntityType>> {
        return actions.map { action ->
            try {
                action.constructors.first().call(context, source, target)
            } catch (e: Exception) {
                throw IllegalArgumentException("Can't create EntityAction. Does it have the proper constructor? $action")
            }
        }
    }
}