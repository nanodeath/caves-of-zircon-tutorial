package com.example.cavesofzircon.view.fragment

import com.example.cavesofzircon.attributes.DisplayableAttribute
import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.types.Player
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Fragment

class PlayerStatsFragment(
    width: Int,
    player: GameEntity<Player>
)  : Fragment{
    override val root = Components.vbox()
        .withSize(width, 30)
        .withSpacing(1)
        .build().apply {
            addComponent(Components.header().withText("Player"))
            player.attributes
                .filterIsInstance<DisplayableAttribute>()
                .forEach { addComponent(it.toComponent(width)) }
        }
}
