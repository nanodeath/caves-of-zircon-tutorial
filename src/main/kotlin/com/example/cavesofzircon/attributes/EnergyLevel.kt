package com.example.cavesofzircon.attributes

import com.example.cavesofzircon.extensions.plus
import com.example.cavesofzircon.extensions.toStringProperty
import org.hexworks.amethyst.api.base.BaseAttribute
import org.hexworks.cobalt.databinding.api.extension.createPropertyFrom
import org.hexworks.zircon.api.Components

class EnergyLevel(initialEnergy: Int, val maxEnergy: Int): BaseAttribute(), DisplayableAttribute {
    var currentEnergy: Int
        get() = currentValueProperty.value
        set(value) {
            currentValueProperty.value = value.coerceAtMost(maxEnergy)
        }

    private val currentValueProperty = createPropertyFrom(initialEnergy)

    override fun toComponent(width: Int) = Components.vbox()
        .withSize(width, 5)
        .build().apply {
            val hungerLabel = Components.label()
                .withSize(width, 1)
                .build()

            hungerLabel.textProperty.updateFrom(currentValueProperty.toStringProperty() + "/" + maxEnergy)

            addComponent(Components.textBox(width).addHeader("Hunger"))
            addComponent(hungerLabel)
        }
}
