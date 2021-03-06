package com.example.cavesofzircon.world

import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.types.Player

class Game(val world: World, val player: GameEntity<Player>) {
    companion object {
        fun create(
            player: GameEntity<Player>,
            world: World
        ) = Game(
            world, player
        )
    }
}
