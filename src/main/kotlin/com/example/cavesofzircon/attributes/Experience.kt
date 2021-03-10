package com.example.cavesofzircon.attributes

import com.example.cavesofzircon.extensions.plus
import org.hexworks.amethyst.api.base.BaseAttribute
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Component

data class Experience(
    val currentXpProperty: Property<Int> = 0.toProperty(),
    val currentLevelProperty: Property<Int> = 1.toProperty()
) : BaseAttribute(), DisplayableAttribute {
    var currentXp: Int by currentXpProperty.asDelegate()
    var currentLevel: Int by currentLevelProperty.asDelegate()

    override fun toComponent(width: Int): Component {
        return Components.vbox()
            .withSize(width, 3)
            .build().apply {
                val xpLabel = Components.label()
                    .withSize(width, 1)
                    .build()
                val levelLabel = Components.label()
                    .withSize(width, 1)
                    .build()

                xpLabel.textProperty.updateFrom("XP: ".toProperty() + currentXpProperty)
                levelLabel.textProperty.updateFrom("Lvl: ".toProperty() + currentLevelProperty)
                addComponent(Components.textBox(width).addHeader("Experience", withNewLine = false))
                addComponents(xpLabel, levelLabel)
            }
    }
}
