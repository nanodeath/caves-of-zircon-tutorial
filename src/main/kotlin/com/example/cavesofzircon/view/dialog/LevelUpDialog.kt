package com.example.cavesofzircon.view.dialog

import com.example.cavesofzircon.attributes.Vision
import com.example.cavesofzircon.attributes.types.ExperienceGainer
import com.example.cavesofzircon.attributes.types.combatStats
import com.example.cavesofzircon.events.logGameEvent
import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.extensions.requiredAttribute
import com.example.cavesofzircon.types.Player
import org.hexworks.zircon.api.ComponentDecorations
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.internal.component.modal.EmptyModalResult

class LevelUpDialog(screen: Screen, player: GameEntity<Player>) : Dialog(screen, false) {
    override val container = Components.vbox()
        .withDecorations(ComponentDecorations.box(BoxType.TOP_BOTTOM_DOUBLE, title = "Ding!"))
        .withSize(30, 15)
        .build().apply {
            val stats = (player as GameEntity<ExperienceGainer>).combatStats
            val vision = player.requiredAttribute<Vision>()

            addComponent(Components.textBox(27)
                .addHeader("Congratulations, you leveled up!")
                .addParagraph("Pick an improvement from the options below:"))

            addComponent(Components.button()
                .withText("Max HP")
                .build().apply {
                    onActivated {
                        stats.maxHpProperty.value += 10
                        logGameEvent("You look healthier.", player)
                        root.close(EmptyModalResult)
                        Processed
                    }
                })

            addComponent(Components.button()
                .withText("Attack")
                .build().apply {
                    onActivated {
                        stats.attackValueProperty.value += 2
                        logGameEvent("You look stronger.", player)
                        root.close(EmptyModalResult)
                        Processed
                    }
                })

            addComponent(Components.button()
                .withText("Defense")
                .build().apply {
                    onActivated {
                        stats.defenseValueProperty.value += 2
                        logGameEvent("You look tougher.", player)
                        root.close(EmptyModalResult)
                        Processed
                    }
                })

            addComponent(Components.button()
                .withText("Vision")
                .build().apply {
                    onActivated {
                        vision.radius++
                        logGameEvent("You look more perceptive.", player)
                        root.close(EmptyModalResult)
                        Processed
                    }
                })
        }
}
