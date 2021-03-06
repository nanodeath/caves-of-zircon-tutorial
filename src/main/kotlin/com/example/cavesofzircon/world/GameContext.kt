package com.example.cavesofzircon.world

import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.types.Player
import org.hexworks.amethyst.api.Context
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.uievent.UIEvent

data class GameContext(
    val world: World,
    val screen: Screen,
    val uiEvent: UIEvent,
    val player: GameEntity<Player>
) : Context