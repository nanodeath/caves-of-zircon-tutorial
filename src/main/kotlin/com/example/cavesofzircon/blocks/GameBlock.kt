package com.example.cavesofzircon.blocks

import com.example.cavesofzircon.builders.EntityFactory
import com.example.cavesofzircon.builders.GameTileRepository.EMPTY
import com.example.cavesofzircon.builders.GameTileRepository.FLOOR
import com.example.cavesofzircon.builders.GameTileRepository.PLAYER
import com.example.cavesofzircon.builders.GameTileRepository.UNREVEALED
import com.example.cavesofzircon.extensions.AnyGameEntity
import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.extensions.occupiesBlock
import com.example.cavesofzircon.extensions.tile
import kotlinx.collections.immutable.persistentMapOf
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.zircon.api.data.BlockTileType
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.base.BaseBlock

class GameBlock(
    private val defaultTile: Tile = FLOOR,
    private val currentEntities: MutableList<GameEntity<EntityType>> = mutableListOf()
) : BaseBlock<Tile>(
    EMPTY,
    tiles = persistentMapOf(BlockTileType.CONTENT to defaultTile)
) {
    init {
        updateContent()
    }

    val isEmptyFloor get() = currentEntities.isEmpty() || currentEntities.singleOrNull() == EntityFactory.fogOfWar
    val entities: Iterable<GameEntity<EntityType>> = currentEntities
    val occupier: AnyGameEntity? get() = currentEntities.firstOrNull { it.occupiesBlock }
    val isOccupied get() = currentEntities.any { it.occupiesBlock }

    fun addEntity(entity: GameEntity<EntityType>) {
        currentEntities.add(entity)
        updateContent()
    }

    fun removeEntity(entity: GameEntity<EntityType>) {
        currentEntities.remove(entity)
        updateContent()
    }

    fun reveal() {
        if (currentEntities.remove(EntityFactory.fogOfWar)) {
            updateContent()
        }
    }

    private fun updateContent() {
        val entityTiles: List<Tile> = currentEntities.map { it.tile }
        content = when {
            PLAYER in entityTiles -> PLAYER
            EntityFactory.fogOfWar in currentEntities -> UNREVEALED
            entityTiles.isNotEmpty() -> entityTiles.first()
            else -> defaultTile
        }
    }

    override fun toString(): String {
        return "GameBlock(entities=$entities)"
    }

    companion object {
        fun createWith(entity: AnyGameEntity) = GameBlock(
            currentEntities = mutableListOf(entity, EntityFactory.fogOfWar)
        )
    }
}
