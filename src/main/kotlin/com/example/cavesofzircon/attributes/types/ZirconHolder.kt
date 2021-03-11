package com.example.cavesofzircon.attributes.types

import com.example.cavesofzircon.attributes.ZirconCounter
import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.extensions.requiredAttribute
import org.hexworks.amethyst.api.entity.EntityType

interface ZirconHolder : EntityType

val GameEntity<ZirconHolder>.zirconCounter get() = requiredAttribute<ZirconCounter>()
