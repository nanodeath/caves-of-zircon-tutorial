package com.example.cavesofzircon.attributes

import org.hexworks.amethyst.api.base.BaseAttribute
import org.hexworks.zircon.api.Components

data class ItemCombatStats(
    val attackValue: Int = 0,
    val defenseValue: Int = 0,
    val combatItemType: String
) : BaseAttribute(), DisplayableAttribute {
    override fun toComponent(width: Int) = Components.textBox(width)
        .addParagraph("Type: $combatItemType", withNewLine = false)
        .addParagraph("Attack: $attackValue", withNewLine = false)
        .addParagraph("Defense: $defenseValue", withNewLine = false)
        .build()
}
