package com.example.cavesofzircon.types

import com.example.cavesofzircon.attributes.types.Armor
import com.example.cavesofzircon.attributes.types.Combatant
import com.example.cavesofzircon.attributes.types.Food
import com.example.cavesofzircon.attributes.types.Weapon
import org.hexworks.amethyst.api.base.BaseEntityType

object Wall : BaseEntityType("wall")
object Fungus : BaseEntityType("fungus"), Combatant
object Bat : BaseEntityType("bat"), Combatant, ItemHolder
object BatMeat : BaseEntityType("bat meat", "Stringy bat meat. It is edible, but not tasty."), Food
object StairsDown : BaseEntityType("stairs down")
object StairsUp : BaseEntityType("stairs up")
object FogOfWarType : BaseEntityType("fog of war")
object Zircon : BaseEntityType("zircon", description = "A small piece of Zircon. Its value is unfathomable."), Item
object Dagger : BaseEntityType(
    name = "Rusty Dagger",
    description = "A small, rusty dagger made of some metal alloy."), Weapon

object Sword : BaseEntityType(
    name = "Iron Sword",
    description = "A shiny sword made of iron. It is a two-hand weapon"), Weapon

object Staff : BaseEntityType(
    name = "Wooden Staff",
    description = "A wooden staff made of birch. It has seen some use"), Weapon

object LightArmor : BaseEntityType(
    name = "Leather Tunic",
    description = "A tunic made of rugged leather. It is very comfortable."), Armor

object MediumArmor : BaseEntityType(
    name = "Chainmail",
    description = "A sturdy chainmail armor made of interlocking iron chains."), Armor

object HeavyArmor : BaseEntityType(
    name = "Platemail",
    description = "A heavy and shiny platemail armor made of bronze."), Armor

object Club : BaseEntityType(
    name = "Club",
    description = "A wooden club. It doesn't give you an edge over your opponent (haha)."), Weapon

object Jacket : BaseEntityType(
    name = "Leather jacket",
    description = "Dirty and rugged jacket made of leather."), Armor

object Zombie : BaseEntityType(name = "zombie"), Combatant, ItemHolder

object Exit : BaseEntityType(name = "exit")
