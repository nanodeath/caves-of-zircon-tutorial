package com.example.cavesofzircon

import com.example.cavesofzircon.view.StartView
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.builder.application.AppConfigBuilder

fun main(args: Array<String>) {
    val grid = SwingApplications.startTileGrid(
        AppConfigBuilder.newBuilder()
            .withDefaultTileset(CP437TilesetResources.rogueYun16x16())
            .build()
    )
    StartView(grid).dock()
}
