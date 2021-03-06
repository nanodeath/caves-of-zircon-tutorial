package com.example.cavesofzircon.world

import com.example.cavesofzircon.GameConfig
import com.example.cavesofzircon.builders.WorldBuilder
import org.hexworks.zircon.api.data.Size3D
import kotlin.random.Random

class Game(val world: World) {
    companion object {
        fun create(
            worldSize: Size3D = GameConfig.WORLD_SIZE,
            visibleSize: Size3D = GameConfig.GAME_AREA_SIZE
        ) = Game(
            WorldBuilder(worldSize, Random(0xDEADBEEF))
                .makeCaves()
                .build(visibleSize)
        )
    }
}