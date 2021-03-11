package com.example.cavesofzircon.attributes

import com.example.cavesofzircon.extensions.plus
import org.hexworks.amethyst.api.base.BaseAttribute
import org.hexworks.cobalt.databinding.api.extension.createPropertyFrom
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Component

data class ZirconCounter(private val zirconCountProperty: Property<Int> = createPropertyFrom(0)) : BaseAttribute(), DisplayableAttribute {
    var zirconCount by zirconCountProperty.asDelegate()

    override fun toComponent(width: Int): Component {
        val zirconProp = "Zircons: ".toProperty() + zirconCountProperty
        return Components.header()
            .withText(zirconProp.value)
            .withSize(width, 1)
            .build().apply {
                textProperty.updateFrom(zirconProp)
            }
    }
}
