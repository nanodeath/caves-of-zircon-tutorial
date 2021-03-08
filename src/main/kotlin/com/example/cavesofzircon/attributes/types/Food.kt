package com.example.cavesofzircon.attributes.types

import com.example.cavesofzircon.attributes.NutritionalValue
import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.extensions.requiredAttribute
import com.example.cavesofzircon.types.Item

interface Food : Item

val GameEntity<Food>.energy: Int get() = requiredAttribute<NutritionalValue>().energy
