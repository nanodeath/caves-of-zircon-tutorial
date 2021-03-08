package com.example.cavesofzircon.systems

import com.example.cavesofzircon.attributes.EnergyLevel
import com.example.cavesofzircon.attributes.types.energy
import com.example.cavesofzircon.attributes.types.energyLevel
import com.example.cavesofzircon.commands.Eat
import com.example.cavesofzircon.events.logGameEvent
import com.example.cavesofzircon.extensions.isPlayer
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet

object DigestiveSystem : BaseFacet<GameContext, Eat>(Eat::class, EnergyLevel::class) {
    override suspend fun receive(message: Eat): Response {
        val (_, creature, food) = message
        creature.energyLevel.currentEnergy += food.energy
        val verb = if (creature.isPlayer) "You eat" else "The $creature eats"
        logGameEvent("$verb the $food", creature)
        return Consumed
    }
}
