package com.example.cavesofzircon.attributes

import com.example.cavesofzircon.extensions.plus
import org.hexworks.amethyst.api.base.BaseAttribute
import org.hexworks.cobalt.databinding.api.extension.createPropertyFrom
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.Components

data class CombatStats(
    val maxHpProperty: Property<Int>,
    val hpProperty: Property<Int> = createPropertyFrom(maxHpProperty.value),
    val attackValueProperty: Property<Int>,
    val defenseValueProperty: Property<Int>
) : BaseAttribute(), DisplayableAttribute {
    val maxHp: Int by maxHpProperty.asDelegate()
    var hp: Int by hpProperty.asDelegate()
    val attackValue: Int by attackValueProperty.asDelegate()
    val defenseValue: Int by defenseValueProperty.asDelegate()


    override fun toComponent(width: Int) = Components.vbox()
        .withSize(width, 5)
        .build().apply {
            val hpLabel = Components.label()
                .withSize(width, 1)
                .build()
            val attackLabel = Components.label()
                .withSize(width, 1)
                .build()
            val defenseLabel = Components.label()
                .withSize(width, 1)
                .build()
            hpLabel.textProperty.updateFrom("HP: ".toProperty() + hpProperty + "/" + maxHpProperty)
            attackLabel.textProperty.updateFrom("Atk: ".toProperty() + attackValueProperty)
            defenseLabel.textProperty.updateFrom("Def: ".toProperty() + defenseValueProperty)

            addComponent(Components.textBox(width).addHeader("Combat.Stats"))
            addComponents(hpLabel, attackLabel, defenseLabel)
        }

    companion object {
        fun create(
            maxHp: Int, hp: Int = maxHp, attackValue: Int, defenseValue: Int
        ) = CombatStats(
            maxHpProperty = createPropertyFrom(maxHp),
            hpProperty = createPropertyFrom(hp),
            attackValueProperty = createPropertyFrom(attackValue),
            defenseValueProperty = createPropertyFrom(defenseValue)
        )
    }
}
