package com.example.cavesofzircon.blocks

import com.example.cavesofzircon.builders.GameTileRepository.EMPTY
import com.example.cavesofzircon.builders.GameTileRepository.FLOOR
import com.example.cavesofzircon.builders.GameTileRepository.WALL
import kotlinx.collections.immutable.persistentMapOf
import org.hexworks.zircon.api.data.BlockTileType
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.base.BaseBlock

class GameBlock(content: Tile = FLOOR) : BaseBlock<Tile>(
    EMPTY,
    tiles = persistentMapOf(BlockTileType.CONTENT to content)
) {
    val isFloor get() = content == FLOOR
    val isWall get() = content == WALL
}
