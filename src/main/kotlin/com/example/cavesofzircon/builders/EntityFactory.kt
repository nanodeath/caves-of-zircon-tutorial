package com.example.cavesofzircon.builders

import com.example.cavesofzircon.attributes.EntityPosition
import com.example.cavesofzircon.attributes.EntityTile
import com.example.cavesofzircon.builders.GameTileRepository.PLAYER
import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.types.Player
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.builder.EntityBuilder
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.newEntityOfType

fun <T : EntityType> newGameEntityOfType(
    type: T,
    init: EntityBuilder<T, GameContext>.() -> Unit
): GameEntity<T> = newEntityOfType(type, init)

object EntityFactory {
    fun newPlayer() = newGameEntityOfType(Player) {
        attributes(EntityPosition(), EntityTile(PLAYER))
        behaviors()
        facets()
    }
}