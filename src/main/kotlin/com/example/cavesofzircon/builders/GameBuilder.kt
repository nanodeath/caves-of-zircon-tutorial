package com.example.cavesofzircon.builders

import com.example.cavesofzircon.GameConfig
import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.extensions.position
import com.example.cavesofzircon.types.Player
import com.example.cavesofzircon.world.Game
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import kotlin.random.Random

class GameBuilder(val worldSize: Size3D, random: Random) {
    private val visibleSize = Size3D.create(
        xLength = GameConfig.WINDOW_WIDTH - GameConfig.SIDEBAR_WIDTH,
        yLength = GameConfig.WINDOW_HEIGHT - GameConfig.LOG_AREA_HEIGHT,
        zLength = 1
    )

    val world = WorldBuilder(worldSize, random)
        .makeCaves()
        .build(visibleSize)

    fun buildGame(): Game {
        prepareWorld()
        val player = addPlayer()
        println("Adding player at ${player.position}")
        return Game.create(player, world)
    }

    private fun prepareWorld() {
        world.scrollUpBy(world.actualSize.zLength)
    }

    private fun addPlayer(): GameEntity<Player> {
        val player = EntityFactory.newPlayer()
        world.addAtEmptyPosition(
            player,
            offset = Position3D.create(0, 0, GameConfig.DUNGEON_LEVELS - 1),
            size = world.visibleSize.copy(zLength = 0)
        )
        return player
    }

    companion object {
        fun create(random: Random) = GameBuilder(GameConfig.WORLD_SIZE, random).buildGame()
    }
}