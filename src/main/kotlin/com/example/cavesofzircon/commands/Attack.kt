package com.example.cavesofzircon.commands

import com.example.cavesofzircon.attributes.types.Combatant
import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.messages.EntityAction
import com.example.cavesofzircon.messages.EntityActionBuilder
import com.example.cavesofzircon.world.GameContext

data class Attack(
    override val context: GameContext,
    override val source: GameEntity<Combatant>,
    override val target: GameEntity<Combatant>
) : EntityAction<Combatant, Combatant> {
    companion object : EntityActionBuilder<Combatant, Combatant> {
        override fun create(
            context: GameContext,
            source: GameEntity<Combatant>,
            target: GameEntity<Combatant>?
        ) = Attack(context, source, target!!)
    }
}
