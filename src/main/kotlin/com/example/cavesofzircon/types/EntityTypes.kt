package com.example.cavesofzircon.types

import com.example.cavesofzircon.attributes.types.Combatant
import com.example.cavesofzircon.attributes.types.Food
import org.hexworks.amethyst.api.base.BaseEntityType

object Wall : BaseEntityType("wall")
object Fungus : BaseEntityType("fungus"), Combatant
object Bat : BaseEntityType("bat"), Combatant, ItemHolder
object BatMeat : BaseEntityType("bat meat", "Stringy bat meat. It is edible, but not tasty."), Food
object StairsDown : BaseEntityType("stairs down")
object StairsUp : BaseEntityType("stairs up")
object FogOfWarType : BaseEntityType("fog of war")
object Zircon : BaseEntityType("zircon", description = "A small piece of Zircon. Its value is unfathomable."), Item
