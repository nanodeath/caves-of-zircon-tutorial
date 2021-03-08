package com.example.cavesofzircon.commands

import com.example.cavesofzircon.attributes.types.EnergyUser
import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.messages.EntityAction
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.entity.EntityType

data class Expend(
    override val context: GameContext, override val source: GameEntity<EnergyUser>,
    override val target: GameEntity<EntityType>? = null, // TODO unused
    val energy: Int

) : EntityAction<EnergyUser, EntityType>
