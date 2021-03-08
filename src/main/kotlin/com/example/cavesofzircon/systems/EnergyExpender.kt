package com.example.cavesofzircon.systems

import com.example.cavesofzircon.attributes.EnergyLevel
import com.example.cavesofzircon.attributes.types.EnergyUser
import com.example.cavesofzircon.attributes.types.energyLevel
import com.example.cavesofzircon.commands.Destroy
import com.example.cavesofzircon.commands.Expend
import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.extensions.takeIfType
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Message
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseActor
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType

object EnergyExpender : BaseActor<GameContext, Expend>(EnergyLevel::class) {
    override val messageType = Expend::class

    override suspend fun update(entity: Entity<EntityType, GameContext>, context: GameContext): Boolean {
        return entity.takeIfType<EnergyUser>()?.let { energyUser ->
            energyUser.receiveMessage(Expend(context, energyUser, energy = 2))
            true
        } ?: false
    }

    override suspend fun receive(message: Expend): Response {
        val (context, creature, _, energy) = message
        creature.energyLevel.currentEnergy -= energy
        checkStarvation(context, creature)
        return Consumed
    }

    private suspend fun checkStarvation(context: GameContext, creature: GameEntity<EnergyUser>) {
        if (creature.energyLevel.currentEnergy <= 0) {
            creature.receiveMessage(Destroy(
                context,
                creature,
                creature,
                cause = "due to starvation"
            ))
        }
    }

    override suspend fun tryReceive(message: Message<GameContext>): Response {
        return if (message is Expend) receive(message) else Pass
    }
}
