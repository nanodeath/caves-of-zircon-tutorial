package com.example.cavesofzircon.systems

import com.example.cavesofzircon.extensions.AnyGameEntity
import com.example.cavesofzircon.extensions.position
import com.example.cavesofzircon.extensions.sameLevelNeighborsShuffled
import com.example.cavesofzircon.messages.MoveTo
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.base.BaseBehavior

object Wanderer : BaseBehavior<GameContext>() {
    override suspend fun update(entity: AnyGameEntity, context: GameContext): Boolean {
        val pos = entity.position
        if (!pos.isUnknown) {
            entity.receiveMessage(MoveTo(context, entity, pos.sameLevelNeighborsShuffled().first()))
            return true
        }
        return false
    }
}