package com.example.cavesofzircon.builders

import org.hexworks.zircon.api.data.StackedTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Symbols

object GameTileRepository {
    val EMPTY = StackedTile.create(Tile.empty())

    val FLOOR = Tile.newBuilder()
        .withName("FLOOR")
        .withCharacter(Symbols.INTERPUNCT)
        .withForegroundColor(GameColors.FLOOR_FOREGROUND)
        .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
        .buildCharacterTile()

    val WALL = Tile.newBuilder()
        .withName("WALL")
        .withCharacter('#')
        .withForegroundColor(GameColors.WALL_FOREGROUND)
        .withBackgroundColor(GameColors.WALL_BACKGROUND)
        .buildCharacterTile()

    val PLAYER = Tile.newBuilder()
        .withName("PLAYER")
        .withCharacter('@')
        .withForegroundColor(GameColors.ACCENT_COLOR)
        .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
        .buildCharacterTile()

    val FUNGUS = Tile.newBuilder()
        .withName("FUNGUS")
        .withCharacter('f')
        .withForegroundColor(GameColors.FUNGUS_COLOR)
        .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
        .buildCharacterTile()

    val BAT = Tile.newBuilder()
        .withName("BAT")
        .withCharacter('b')
        .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
        .withForegroundColor(GameColors.BAT_COLOR)
        .buildCharacterTile()

    val STAIRS_UP = Tile.newBuilder()
        .withName("STAIRS_UP")
        .withCharacter('<')
        .withForegroundColor(GameColors.ACCENT_COLOR)
        .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
        .buildCharacterTile()

    val STAIRS_DOWN = Tile.newBuilder()
        .withName("STAIRS_DOWN")
        .withCharacter('>')
        .withForegroundColor(GameColors.ACCENT_COLOR)
        .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
        .buildCharacterTile()

    val UNREVEALED = Tile.newBuilder()
        .withName("UNREVEALED")
        .withCharacter(' ')
        .withBackgroundColor(GameColors.UNREVEALED_COLOR)
        .buildCharacterTile()

    val ZIRCON = Tile.newBuilder()
        .withCharacter(',')
        .withForegroundColor(GameColors.ZIRCON_COLOR)
        .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
        .buildCharacterTile()
}
