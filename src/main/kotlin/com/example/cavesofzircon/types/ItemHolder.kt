package com.example.cavesofzircon.types

import com.example.cavesofzircon.attributes.Inventory
import com.example.cavesofzircon.extensions.GameItem
import com.example.cavesofzircon.extensions.GameItemHolder
import org.hexworks.amethyst.api.entity.EntityType

interface ItemHolder : EntityType

fun GameItemHolder.addItem(item: GameItem) = inventory.addItem(item)
fun GameItemHolder.removeItem(item: GameItem) = inventory.removeItem(item)

val GameItemHolder.inventory get() = findAttributeOrNull(Inventory::class)!!
