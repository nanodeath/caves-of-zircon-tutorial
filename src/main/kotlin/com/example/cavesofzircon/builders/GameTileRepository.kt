package com.example.cavesofzircon.builders

import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Symbols

object GameTileRepository {
    val EMPTY = Tile.empty()

    val FLOOR = Tile.newBuilder()
        .withCharacter(Symbols.INTERPUNCT)
        .withForegroundColor(GameColors.FLOOR_FOREGROUND)
        .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
        .buildCharacterTile()

    val WALL = Tile.newBuilder()
        .withCharacter('#')
        .withForegroundColor(GameColors.WALL_FOREGROUND)
        .withBackgroundColor(GameColors.WALL_BACKGROUND)
        .buildCharacterTile()
}