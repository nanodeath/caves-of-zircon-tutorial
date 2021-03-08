package com.example.cavesofzircon.attributes.types

import com.example.cavesofzircon.attributes.EnergyLevel
import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.extensions.requiredAttribute
import org.hexworks.amethyst.api.entity.EntityType

interface EnergyUser : EntityType

val GameEntity<EnergyUser>.energyLevel: EnergyLevel get() = requiredAttribute()
