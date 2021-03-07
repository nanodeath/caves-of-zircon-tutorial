package com.example.cavesofzircon.builders

import com.example.cavesofzircon.GameConfig
import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.extensions.position
import com.example.cavesofzircon.types.FogOfWar
import com.example.cavesofzircon.types.Player
import com.example.cavesofzircon.world.Game
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size
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
        .connectLevels()
        .build(visibleSize)

    fun buildGame(): Game {
        prepareWorld()
        val player = addPlayer()
        addFungi()
        println("Adding player at ${player.position}")
        val game = Game.create(player, world)
        world.addWorldEntity(FogOfWar(game))
        return game
    }

    private fun prepareWorld() {
        world.scrollUpBy(world.actualSize.zLength)
    }

    private fun addPlayer(): GameEntity<Player> {
        return EntityFactory.newPlayer().addToWorld(
            atLevel = GameConfig.DUNGEON_LEVELS - 1,
            atArea = world.visibleSize.to2DSize()
        )
    }

    private fun addFungi() {
        repeat(world.actualSize.zLength) { level ->
            repeat(GameConfig.FUNGI_PER_LEVEL) {
                EntityFactory.newFungus().addToWorld(atLevel = level)
            }
        }
    }

    private fun <T : EntityType> GameEntity<T>.addToWorld(
        atLevel: Int,
        atArea: Size = world.actualSize.to2DSize()
    ) : GameEntity<T> {
        world.addAtEmptyPosition(this,
            offset = Position3D.defaultPosition().withZ(atLevel),
            size = Size3D.from2DSize(atArea)
        )
        return this
    }

    companion object {
        fun create(random: Random) = GameBuilder(GameConfig.WORLD_SIZE, random).buildGame()
    }
}