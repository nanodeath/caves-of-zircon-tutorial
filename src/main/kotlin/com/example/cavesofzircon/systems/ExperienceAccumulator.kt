package com.example.cavesofzircon.systems

import com.example.cavesofzircon.attributes.CombatStats
import com.example.cavesofzircon.attributes.types.ExperienceGainer
import com.example.cavesofzircon.attributes.types.combatStats
import com.example.cavesofzircon.attributes.types.experience
import com.example.cavesofzircon.commands.EntityDestroyed
import com.example.cavesofzircon.events.PlayerGainedLevel
import com.example.cavesofzircon.events.logGameEvent
import com.example.cavesofzircon.extensions.*
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.zircon.internal.Zircon
import kotlin.math.min
import kotlin.math.pow

object ExperienceAccumulator : BaseFacet<GameContext, EntityDestroyed>(EntityDestroyed::class) {
    override suspend fun receive(message: EntityDestroyed): Response {
        val (_, defender, attacker) = message
        attacker.takeIfType<ExperienceGainer>()?.let { experienceGainer ->
            val xp = experienceGainer.experience
            val stats = experienceGainer.combatStats
            val defenderHp = defender.optionalAttribute<CombatStats>()?.maxHp ?: 0
            val amount = (defenderHp + defender.attackValue + defender.defenseValue) - xp.currentLevel * 2
            if (amount > 0) {
                xp.currentXp += amount
                while (xp.currentXp > xp.currentLevel.toDouble().pow(1.5) * 20) {
                    xp.currentLevel++
                    logGameEvent("$attacker advanced to level ${xp.currentLevel}.", attacker)
                    stats.hpProperty.value = min(stats.hp + xp.currentLevel * 2, stats.maxHp)
                    if (attacker.isPlayer) {
                        Zircon.eventBus.publish(PlayerGainedLevel(attacker))
                    }
                }
            }
        }
        return Consumed
    }
}
