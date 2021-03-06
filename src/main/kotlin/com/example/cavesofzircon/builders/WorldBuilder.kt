package com.example.cavesofzircon.builders

import com.example.cavesofzircon.blocks.GameBlock
import com.example.cavesofzircon.extensions.sameLevelNeighborsShuffled
import com.example.cavesofzircon.world.World
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import kotlin.random.Random

class WorldBuilder(private val worldSize: Size3D, private val random: Random) {
    private val width = worldSize.xLength
    private val height = worldSize.zLength
    private var blocks = mutableMapOf<Position3D, GameBlock>()

    fun makeCaves() = randomizeTiles().smooth(8)

    fun build(visibleSize: Size3D) = World(blocks, visibleSize, worldSize)

    private fun randomizeTiles(): WorldBuilder {
        forAllPositions { pos ->
            blocks[pos] = if (random.nextBoolean()) GameBlockFactory.floor() else GameBlockFactory.wall()
        }
        return this
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
                        if (block.isFloor) {
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


    private fun forAllPositions(fn: (Position3D) -> Unit) {
        worldSize.fetchPositions().forEach(fn)
    }
}