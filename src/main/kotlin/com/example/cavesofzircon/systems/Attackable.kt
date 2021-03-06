package com.example.cavesofzircon.systems

import com.example.cavesofzircon.attributes.types.combatStats
import com.example.cavesofzircon.commands.Attack
import com.example.cavesofzircon.commands.Destroy
import com.example.cavesofzircon.events.logGameEvent
import com.example.cavesofzircon.extensions.attackValue
import com.example.cavesofzircon.extensions.defenseValue
import com.example.cavesofzircon.extensions.isPlayer
import com.example.cavesofzircon.extensions.noHealthLeft
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet
import kotlin.math.max

object Attackable : BaseFacet<GameContext, Attack>(Attack::class) {
    override suspend fun receive(message: Attack): Response {
        val (context, attacker, target) = message
        return if (attacker.isPlayer || target.isPlayer) {
            val damage = max(1, attacker.attackValue - target.defenseValue)
            val finalDamage = (context.world.random.nextFloat() * damage).toInt() + 1
            target.combatStats.hp -= finalDamage
            logGameEvent("The $attacker hit the $target for $finalDamage", this)

            if (target.noHealthLeft) {
                target.receiveMessage(
                    Destroy(
                        context = context,
                        source = attacker,
                        target = target,
                        cause = "after receiving a blow to the head"
                    )
                )
            }
            Consumed
        } else Pass
    }
}
