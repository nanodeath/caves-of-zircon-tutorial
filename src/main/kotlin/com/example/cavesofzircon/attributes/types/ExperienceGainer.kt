package com.example.cavesofzircon.attributes.types

import com.example.cavesofzircon.attributes.CombatStats
import com.example.cavesofzircon.attributes.Experience
import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.extensions.requiredAttribute
import org.hexworks.amethyst.api.entity.EntityType

interface ExperienceGainer : EntityType

val GameEntity<ExperienceGainer>.experience: Experience get() = requiredAttribute()
val GameEntity<ExperienceGainer>.combatStats: CombatStats get() = requiredAttribute()
