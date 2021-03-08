package com.example.cavesofzircon.types

import com.example.cavesofzircon.attributes.EntityTile
import com.example.cavesofzircon.attributes.ItemIcon
import com.example.cavesofzircon.extensions.GameEntity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.zircon.api.data.Tile

interface Item : EntityType

val GameEntity<Item>.tile: Tile get() = findAttributeOrNull(EntityTile::class)?.tile ?: throw IllegalStateException()
val GameEntity<Item>.iconTile: Tile get() = findAttributeOrNull(ItemIcon::class)?.iconTile ?: throw IllegalStateException()
