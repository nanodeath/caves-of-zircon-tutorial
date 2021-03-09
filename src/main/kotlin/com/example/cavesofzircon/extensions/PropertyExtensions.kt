package com.example.cavesofzircon.extensions

import org.hexworks.cobalt.databinding.api.binding.bindPlusWith
import org.hexworks.cobalt.databinding.api.extension.createPropertyFrom
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.value.ObservableValue

fun ObservableValue<Int>.toStringProperty(): ObservableValue<String> =
    createPropertyFrom("").apply {
        this.updateFrom(this@toStringProperty) { it.toString() }
    }

operator fun ObservableValue<String>.plus(literal: String): ObservableValue<String> =
    this + literal.toProperty()

operator fun ObservableValue<String>.plus(literal: Int): ObservableValue<String> =
    this + literal.toProperty().toStringProperty()

operator fun ObservableValue<String>.plus(property: ObservableValue<String>): ObservableValue<String> =
    bindPlusWith(property)

@JvmName("plusInt")
operator fun ObservableValue<String>.plus(property: ObservableValue<Int>): ObservableValue<String> =
    this + property.toStringProperty()
