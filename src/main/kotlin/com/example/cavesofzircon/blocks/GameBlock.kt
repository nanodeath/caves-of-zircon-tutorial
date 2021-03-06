package com.example.cavesofzircon.blocks

import com.example.cavesofzircon.builders.GameTileRepository.EMPTY
import com.example.cavesofzircon.builders.GameTileRepository.FLOOR
import com.example.cavesofzircon.builders.GameTileRepository.PLAYER
import com.example.cavesofzircon.builders.GameTileRepository.WALL
import com.example.cavesofzircon.extensions.GameEntity
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
    val isFloor get() = content == FLOOR
    val isWall get() = content == WALL

    val isEmptyFloor get() = currentEntities.isEmpty()
    val entities: Iterable<GameEntity<EntityType>> = currentEntities

    fun addEntity(entity: GameEntity<EntityType>) {
        currentEntities.add(entity)
        updateContent()
    }

    fun removeEntity(entity: GameEntity<EntityType>) {
        currentEntities.remove(entity)
        updateContent()
    }

    private fun updateContent() {
        val entityTiles = currentEntities.map { it.tile }
        content = when {
            entityTiles.contains(PLAYER) -> PLAYER
            entityTiles.isNotEmpty() -> entityTiles.first()
            else -> defaultTile
        }
    }
}
