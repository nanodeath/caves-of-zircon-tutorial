package com.example.cavesofzircon.world

import com.example.cavesofzircon.blocks.GameBlock
import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.extensions.position
import org.hexworks.amethyst.api.Engine
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.zircon.api.builder.game.GameAreaBuilder
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.GameArea
import kotlin.random.Random

class World(
    startingBlocks: Map<Position3D, GameBlock>,
    visibleSize: Size3D,
    actualSize: Size3D,
    private val random: Random
) : GameArea<Tile, GameBlock> by GameAreaBuilder.newBuilder<Tile, GameBlock>()
    .withVisibleSize(visibleSize)
    .withActualSize(actualSize)
    .build() {

    private val engine: Engine<GameContext> = Engine.create()

    init {
        for ((pos, block) in startingBlocks) {
            setBlockAt(pos, block)
            for (entity in block.entities) {
                engine.addEntity(entity)
                entity.position = pos
            }
        }
    }

    fun addEntity(entity: Entity<EntityType, GameContext>, position: Position3D) {
        entity.position = position
        engine.addEntity(entity)
        fetchBlockAtOrNull(position)!!.addEntity(entity)
    }

    fun addAtEmptyPosition(
        entity: GameEntity<EntityType>,
        offset: Position3D = Position3D.create(0, 0, 0),
        size: Size3D = actualSize
    ) = findEmptyLocationWithin(offset, size)?.let { location ->
        addEntity(entity, location)
        true
    } ?: false

    fun findEmptyLocationWithin(offset: Position3D, size: Size3D): Position3D? {
        val maxTries = 10
        return generateSequence {
            Position3D.create(
                x = randomInt(size.xLength) + offset.x,
                y = randomInt(size.yLength) + offset.y,
                z = randomInt(size.zLength) + offset.z
            )
        }
            .take(maxTries)
            .firstOrNull { position ->
                fetchBlockAtOrNull(position)?.isEmptyFloor == true
            }
            ?: throw IllegalStateException("Failed after $maxTries attempts")
    }

    private fun randomInt(int: Int) = if (int == 0) 0 else random.nextInt(int)
}
