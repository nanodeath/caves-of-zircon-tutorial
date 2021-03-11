package com.example.cavesofzircon.systems

import com.example.cavesofzircon.attributes.types.zirconCounter
import com.example.cavesofzircon.blocks.GameBlock
import com.example.cavesofzircon.commands.MoveDown
import com.example.cavesofzircon.commands.MoveUp
import com.example.cavesofzircon.events.PlayerWonTheGame
import com.example.cavesofzircon.events.logGameEvent
import com.example.cavesofzircon.extensions.position
import com.example.cavesofzircon.extensions.takeIfType
import com.example.cavesofzircon.types.Exit
import com.example.cavesofzircon.types.Player
import com.example.cavesofzircon.types.StairsDown
import com.example.cavesofzircon.types.StairsUp
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.zircon.internal.Zircon

object StairClimber : BaseFacet<GameContext, MoveUp>(MoveUp::class) {
    override suspend fun receive(message: MoveUp): Response {
        val (context, creature, _) = message
        val world = context.world
        val pos = creature.position
        world.fetchBlockAtOrNull(pos)?.takeIf { it.hasStairsUp }
            ?.also {
                world.moveEntity(creature, pos.withRelativeZ(1))
                // TODO only if player
                logGameEvent("You move up one level...", creature)
                world.scrollOneUp()
            }
            ?: run {
                // TODO only if player
                logGameEvent("You jump up and try to reach the ceiling. You fail.", creature)
            }
        return Consumed
    }

    private val GameBlock.hasStairsUp get() = entities.any { it.type == StairsUp }
}

object StairDescender : BaseFacet<GameContext, MoveDown>(MoveDown::class) {
    override suspend fun receive(message: MoveDown): Response {
        val (context, creature, _) = message
        val world = context.world
        val pos = creature.position
        world.fetchBlockAtOrNull(pos)
            ?.also { block ->
                when {
                    block.hasStairsDown -> {
                        world.moveEntity(creature, pos.withRelativeZ(-1))
//                if (creature == context.player) {
                        logGameEvent("You move down one level...", creature)
                        world.scrollOneDown()
//                }
                    }
                    block.hasExit -> {
                        creature.takeIfType<Player>()?.let {
                            Zircon.eventBus.publish(PlayerWonTheGame(it.zirconCounter.zirconCount, it))
                        }
                    }
                }
            }
            ?: run {
//              if (creature == context.player) {
                logGameEvent("You search for a trapdoor, but you find nothing.", creature)
//              }
            }
        return Consumed
    }

    private val GameBlock.hasStairsDown get() = entities.any { it.type == StairsDown }
    private val GameBlock.hasExit get() = this.entities.any { it.type == Exit }
}
