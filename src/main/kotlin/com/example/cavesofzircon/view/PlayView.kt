package com.example.cavesofzircon.view

import com.example.cavesofzircon.GameConfig
import com.example.cavesofzircon.builders.GameBuilder
import com.example.cavesofzircon.builders.GameTileRepository
import com.example.cavesofzircon.events.GameLogEvent
import com.example.cavesofzircon.events.PlayerDied
import com.example.cavesofzircon.events.PlayerGainedLevel
import com.example.cavesofzircon.events.PlayerWonTheGame
import com.example.cavesofzircon.view.dialog.LevelUpDialog
import com.example.cavesofzircon.view.fragment.PlayerStatsFragment
import com.example.cavesofzircon.world.Game
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.events.api.DisposeSubscription
import org.hexworks.cobalt.events.api.KeepSubscription
import org.hexworks.cobalt.events.api.subscribeTo
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.game.ProjectionMode
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.uievent.*
import org.hexworks.zircon.api.view.base.BaseView
import org.hexworks.zircon.internal.Zircon
import org.hexworks.zircon.internal.game.impl.GameAreaComponentRenderer
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.random.Random

class PlayView(
    private val grid: TileGrid,
    private val game: Game = GameBuilder.create(Random(0xDEADBEEF)),
    theme: ColorTheme = GameConfig.THEME
) : BaseView(grid, theme) {
    init {
        Components.panel()
            .withSize(GameConfig.SIDEBAR_WIDTH, GameConfig.WINDOW_HEIGHT)
            .withDecorations(box())
            .build()
            .let { sidebar ->
                screen.addComponent(sidebar)
                sidebar.addFragment(PlayerStatsFragment(
                    width = sidebar.contentSize.width,
                    player = game.player
                ))
            }

        Components.logArea()
            .withDecorations(box(title = "Log"))
            .withSize(GameConfig.GAME_AREA_SIZE.xLength, GameConfig.LOG_AREA_HEIGHT)
            .withAlignmentWithin(screen, ComponentAlignment.BOTTOM_RIGHT)
            .build()
            .let {
                screen.addComponent(it)
                Zircon.eventBus.subscribeTo<GameLogEvent> { event ->
                    it.addParagraph(paragraph = event.text, withNewLine = false, withTypingEffectSpeedInMs = 10)
                    KeepSubscription
                }
            }

        Components.panel()
            .withSize(game.world.visibleSize.to2DSize())
            .withComponentRenderer(
                GameAreaComponentRenderer(
                    gameArea = game.world,
                    projectionMode = ProjectionMode.TOP_DOWN.toProperty(),
                    fillerTile = GameTileRepository.FLOOR
                )
            )
            .withAlignmentWithin(screen, ComponentAlignment.TOP_RIGHT)
            .build()
            .let { screen.addComponent(it) }

        val currentlyUpdating = AtomicBoolean(false)
        screen.handleKeyboardEvents(KeyboardEventType.KEY_PRESSED) { event: KeyboardEvent, phase: UIEventPhase ->
            if (currentlyUpdating.compareAndSet(false, true)) {
                game.world.update(screen, event, game).invokeOnCompletion { currentlyUpdating.set(false) }
                Processed
            } else {
                PreventDefault
            }
        }
    }

    override fun onDock() {
        Zircon.eventBus.subscribeTo<PlayerGainedLevel> {
            screen.openModal(LevelUpDialog(screen, game.player))
            KeepSubscription
        }
        Zircon.eventBus.subscribeTo<PlayerWonTheGame> {
            replaceWith(WinView(grid, it.zircons))
            DisposeSubscription
        }
        Zircon.eventBus.subscribeTo<PlayerDied> {
            replaceWith(LoseView(grid, it.cause))
            DisposeSubscription
        }
    }
}
