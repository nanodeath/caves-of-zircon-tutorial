package com.example.cavesofzircon.builders

import com.example.cavesofzircon.blocks.GameBlock
import com.example.cavesofzircon.extensions.sameLevelNeighborsShuffled
import com.example.cavesofzircon.world.World
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import kotlin.random.Random

class WorldBuilder(private val worldSize: Size3D, private val random: Random) {
    private val width = worldSize.xLength
    private val height = worldSize.yLength
    private val depth = worldSize.zLength
    private var blocks = mutableMapOf<Position3D, GameBlock>()

    fun makeCaves() = randomizeTiles().smooth(8)
    fun connectLevels() = apply {
        for (i in 1 until depth) {
            connectRegionDown(i)
        }
    }

    fun build(visibleSize: Size3D) = World(blocks, visibleSize, worldSize, random)

    private fun randomizeTiles() = apply {
        forAllPositions { pos ->
            blocks[pos] = if (random.nextBoolean()) GameBlockFactory.floor() else GameBlockFactory.wall()
        }
    }

    private fun smooth(iterations: Int): WorldBuilder {
        val newBlocks = mutableMapOf<Position3D, GameBlock>()

        repeat(iterations) {
            forAllPositions { pos ->
                val (x, y, z) = pos
                var floors = 0
                var rocks = 0
                pos.sameLevelNeighborsShuffled().plus(pos).forEach { neighbor ->
                    blocks[neighbor]?.let { block ->
                        if (block.isEmptyFloor) {
                            floors++
                        } else {
                            rocks++
                        }
                    }
                }
                newBlocks[Position3D.create(x, y, z)] =
                    if (floors >= rocks) GameBlockFactory.floor() else GameBlockFactory.wall()
            }
            blocks = newBlocks
        }

        return this
    }

    private fun generateRandomFloorPositionsOn(level: Int) = sequence {
        while(true) {
            Position3D.create(
                x = random.nextInt(width - 1),
                y = random.nextInt(height - 1),
                z = level
            )
                .takeIf { blocks[it].isEmptyFloor() }
                ?.let { yield(it) }
        }
    }

    private fun connectRegionDown(currentLevel: Int) {
        val posToConnect = generateRandomFloorPositionsOn(currentLevel)
            .take(1000)
            .firstOrNull { pos -> blocks[pos].isEmptyFloor() && blocks[pos.below()].isEmptyFloor() }
            ?: throw IllegalStateException("Unable to connect $currentLevel to level below")
        blocks[posToConnect] = GameBlockFactory.stairsDown()
        blocks[posToConnect.below()] = GameBlockFactory.stairsUp()
    }

    private fun GameBlock?.isEmptyFloor() = this?.isEmptyFloor ?: false
    private fun Position3D.below() = copy(z = z - 1)

    private fun forAllPositions(fn: (Position3D) -> Unit) {
        worldSize.fetchPositions().forEach(fn)
    }
}