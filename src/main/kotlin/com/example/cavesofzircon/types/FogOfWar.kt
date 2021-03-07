package com.example.cavesofzircon.types

import com.example.cavesofzircon.extensions.position
import com.example.cavesofzircon.world.Game
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.Message
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseEntity
import org.hexworks.cobalt.logging.api.LoggerFactory

class FogOfWar(game: Game) : BaseEntity<FogOfWarType, GameContext>(FogOfWarType) {
    private val world = game.world
    private val player = game.player
    private val size = game.world.actualSize

    init {
        updateFOW()
    }

    override val needsUpdate: Boolean get() = true

    override suspend fun update(context: GameContext): Boolean {
        updateFOW()
        return true
    }

    private fun updateFOW() {
        for (position in world.findVisiblePositionsFor(player)) {
            world.makeVisible(position.toPosition3D(player.position.z))
        }
    }

    override suspend fun receiveMessage(message: Message<GameContext>): Response {
        log.debug { "Dropping $message" }
        return Pass
    }

    override suspend fun sendMessage(message: Message<GameContext>): Boolean {
        log.debug { "Dropping $message" }
        return false
    }

    private companion object {
        private val log = LoggerFactory.getLogger(FogOfWar::class)
    }
}