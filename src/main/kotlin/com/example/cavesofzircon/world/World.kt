package com.example.cavesofzircon.world

import com.example.cavesofzircon.attributes.Vision
import com.example.cavesofzircon.blocks.GameBlock
import com.example.cavesofzircon.extensions.AnyGameEntity
import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.extensions.blocksVision
import com.example.cavesofzircon.extensions.position
import kotlinx.coroutines.Job
import org.hexworks.amethyst.api.Engine
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.internal.TurnBasedEngine
import org.hexworks.zircon.api.builder.game.GameAreaBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.shape.EllipseFactory
import org.hexworks.zircon.api.shape.LineFactory
import org.hexworks.zircon.api.uievent.UIEvent
import kotlin.random.Random

class World(
    startingBlocks: Map<Position3D, GameBlock>,
    visibleSize: Size3D,
    actualSize: Size3D,
    val random: Random
) : GameArea<Tile, GameBlock> by GameAreaBuilder.newBuilder<Tile, GameBlock>()
    .withVisibleSize(visibleSize)
    .withActualSize(actualSize)
    .build() {

    private val engine: TurnBasedEngine<GameContext> = Engine.create()

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

    fun addWorldEntity(entity: AnyGameEntity) {
        engine.addEntity(entity)
    }

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
    }

    fun update(screen: Screen, uiEvent: UIEvent, game: Game): Job {
        return engine.executeTurn(
            GameContext(
                world = this,
                screen = screen,
                uiEvent = uiEvent,
                player = game.player
            )
        )
    }

    fun moveEntity(entity: GameEntity<EntityType>, position: Position3D): Boolean {
        val oldBlock = fetchBlockAtOrNull(entity.position)
        val newBlock = fetchBlockAtOrNull(position)
        return if (oldBlock != null && newBlock != null) {
            oldBlock.removeEntity(entity)
            entity.position = position
            newBlock.addEntity(entity)
            true
        } else false
    }

    fun removeEntity(entity: AnyGameEntity) {
        fetchBlockAtOrNull(entity.position)?.removeEntity(entity)
        engine.removeEntity(entity)
        entity.position = Position3D.unknown()
    }

    fun isVisionBlockedAt(pos: Position3D): Boolean =
        fetchBlockAtOrNull(pos)?.entities?.any(AnyGameEntity::blocksVision) ?: false

    fun findVisiblePositionsFor(entity: AnyGameEntity): Iterable<Position> {
        val centerPos = entity.position.to2DPosition()
        val visionRadius = entity.findAttributeOrNull(Vision::class)?.radius ?: return emptyList()
        return EllipseFactory.buildEllipse(
            fromPosition = centerPos,
            toPosition = centerPos.withRelativeX(visionRadius).withRelativeY(visionRadius)
        )
            .positions
            .flatMap { ringPos ->
                // TODO make this more functional
                val result = mutableListOf<Position>()
                val iter = LineFactory.buildLine(centerPos, ringPos).iterator()
                do {
                    val next = iter.next()
                    result.add(next)
                } while (iter.hasNext() && !isVisionBlockedAt(Position3D.from2DPosition(next, entity.position.z)))
                result
            }
    }

    fun makeVisible(position: Position3D) {
        val current = blocks[position] ?: return
        current.reveal()
    }
    private fun randomInt(int: Int) = if (int == 0) 0 else random.nextInt(int)
}