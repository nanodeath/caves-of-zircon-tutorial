package com.example.cavesofzircon.types

import com.example.cavesofzircon.attributes.types.Combatant
import org.hexworks.amethyst.api.base.BaseEntityType

object Wall : BaseEntityType("wall")
object Fungus : BaseEntityType("fungus"), Combatant
object StairsDown : BaseEntityType("stairs down")
object StairsUp : BaseEntityType("stairs up")
object FogOfWarType : BaseEntityType("fog of war")
