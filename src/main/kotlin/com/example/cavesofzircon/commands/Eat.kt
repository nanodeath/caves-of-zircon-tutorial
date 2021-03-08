package com.example.cavesofzircon.commands

import com.example.cavesofzircon.attributes.types.EnergyUser
import com.example.cavesofzircon.attributes.types.Food
import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.messages.EntityAction
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.entity.Entity

data class Eat(
    override val context: GameContext,
    override val source: Entity<EnergyUser, GameContext>,
    override val target: GameEntity<Food>
) : EntityAction<EnergyUser, Food>
