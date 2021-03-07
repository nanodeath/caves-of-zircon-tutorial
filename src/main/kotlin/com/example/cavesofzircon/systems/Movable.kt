package com.example.cavesofzircon.systems

import com.example.cavesofzircon.extensions.position
import com.example.cavesofzircon.extensions.tryActionsOn
import com.example.cavesofzircon.messages.MoveCamera
import com.example.cavesofzircon.messages.MoveTo
import com.example.cavesofzircon.types.Player
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.MessageResponse
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet

object Movable : BaseFacet<GameContext, MoveTo>(MoveTo::class) {
    override suspend fun receive(message: MoveTo): Response {
        val (context, entity, position) = message
        val world = context.world
        val previousPosition = entity.position

        world.fetchBlockAtOrNull(position)?.let { block ->
            block.occupier?.let { return entity.tryActionsOn(context, it) }

            return if (world.moveEntity(entity, position)) {
                if (entity.type == Player) {
                    MessageResponse(
                        MoveCamera(
                            context, entity, previousPosition
                        )
                    )
                } else {
                    Consumed
                }
            } else {
                Pass
            }
        }
        return Pass
    }
}