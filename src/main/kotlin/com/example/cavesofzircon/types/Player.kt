package com.example.cavesofzircon.types

import com.example.cavesofzircon.attributes.types.Combatant
import com.example.cavesofzircon.attributes.types.EnergyUser
import com.example.cavesofzircon.attributes.types.ExperienceGainer
import com.example.cavesofzircon.attributes.types.ZirconHolder
import org.hexworks.amethyst.api.base.BaseEntityType

object Player : BaseEntityType(name = "player"), Combatant, ItemHolder, EnergyUser, EquipmentHolder, ExperienceGainer,
    ZirconHolder
