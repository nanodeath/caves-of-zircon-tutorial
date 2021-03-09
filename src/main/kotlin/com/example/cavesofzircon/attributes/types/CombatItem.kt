package com.example.cavesofzircon.attributes.types

import com.example.cavesofzircon.attributes.ItemCombatStats
import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.extensions.requiredAttribute
import com.example.cavesofzircon.types.Item

interface CombatItem : Item

val GameEntity<CombatItem>.attackValue get() = requiredAttribute<ItemCombatStats>().attackValue
val GameEntity<CombatItem>.defenseValue get() = requiredAttribute<ItemCombatStats>().defenseValue
