package com.example.cavesofzircon.attributes.types

import com.example.cavesofzircon.attributes.CombatStats
import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.extensions.requiredAttribute
import org.hexworks.amethyst.api.entity.EntityType

interface Combatant : EntityType

val GameEntity<Combatant>.combatStats get() = requiredAttribute<CombatStats>()