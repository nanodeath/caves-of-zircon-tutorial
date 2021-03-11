package com.example.cavesofzircon.view

import com.example.cavesofzircon.GameConfig
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.view.base.BaseView
import kotlin.system.exitProcess

class LoseView(
    private val grid: TileGrid,
    private val causeOfDeath: String,
) : BaseView(grid, GameConfig.THEME) {
    init {
        val header = Components.textBox(30)
            .addHeader("Game Over")
            .addParagraph(causeOfDeath)
            .withAlignmentWithin(screen, ComponentAlignment.CENTER)
            .build()

        val restartButton = Components.button()
            .withAlignmentAround(header, ComponentAlignment.BOTTOM_LEFT)
            .withText("Restart")
            .withDecorations(box())
            .build()

        val exitButton = Components.button()
            .withAlignmentAround(header, ComponentAlignment.BOTTOM_RIGHT)
            .withText("Quit")
            .withDecorations(box())
            .build()

        restartButton.onActivated {
            replaceWith(PlayView(grid))
        }

        exitButton.onActivated {
            exitProcess(0)
        }

        screen.addComponents(header, restartButton, exitButton)
    }
}
