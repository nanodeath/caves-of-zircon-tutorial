package com.example.cavesofzircon.types

import com.example.cavesofzircon.attributes.types.Combatant
import org.hexworks.amethyst.api.base.BaseEntityType

object Wall : BaseEntityType("wall")
object Fungus : BaseEntityType("fungus"), Combatant
