package com.example.cavesofzircon

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.builder.application.AppConfigBuilder
import org.hexworks.zircon.api.data.Size3D

object GameConfig {
    // Game
    const val DUNGEON_LEVELS = 2
    const val FUNGI_PER_LEVEL = 15
    const val MAXIMUM_FUNGUS_SPREAD = 20
    const val BATS_PER_LEVEL = 10
    const val ZIRCONS_PER_LEVEL = 20
    const val WEAPONS_PER_LEVEL = 3
    const val ARMOR_PER_LEVEL = 3

    // Look & Feel
    val TILESET = CP437TilesetResources.rogueYun16x16()
    val THEME = ColorThemes.zenburnVanilla()
    const val SIDEBAR_WIDTH = 18
    const val LOG_AREA_HEIGHT = 8

    // Sizing
    const val WINDOW_WIDTH = 80
    const val WINDOW_HEIGHT = 50

    val WORLD_SIZE = Size3D.create(WINDOW_WIDTH * 2, WINDOW_HEIGHT * 2, DUNGEON_LEVELS)
    val GAME_AREA_SIZE = Size3D.create(
        xLength = WINDOW_WIDTH - SIDEBAR_WIDTH,
        yLength = WINDOW_HEIGHT - LOG_AREA_HEIGHT,
        zLength = DUNGEON_LEVELS
    )

    fun buildAppConfig() = AppConfigBuilder.newBuilder()
        .withDefaultTileset(TILESET)
        .withSize(WINDOW_WIDTH, WINDOW_HEIGHT)
        .build()
}
