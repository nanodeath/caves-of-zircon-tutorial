package com.example.cavesofzircon.builders

import com.example.cavesofzircon.attributes.EntityActions
import com.example.cavesofzircon.attributes.EntityPosition
import com.example.cavesofzircon.attributes.EntityTile
import com.example.cavesofzircon.builders.GameTileRepository.PLAYER
import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.flags.BlockOccupier
import com.example.cavesofzircon.messages.Dig
import com.example.cavesofzircon.systems.CameraMover
import com.example.cavesofzircon.systems.Diggable
import com.example.cavesofzircon.systems.InputReceiver
import com.example.cavesofzircon.systems.Movable
import com.example.cavesofzircon.types.Player
import com.example.cavesofzircon.types.Wall
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.builder.EntityBuilder
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.newEntityOfType

fun <T : EntityType> newGameEntityOfType(
    type: T,
    init: EntityBuilder<T, GameContext>.() -> Unit
): GameEntity<T> = newEntityOfType(type, init)

object EntityFactory {
    fun newPlayer(): GameEntity<Player> = newGameEntityOfType(Player) {
        attributes(
            EntityPosition(),
            EntityTile(PLAYER),
            EntityActions(Dig::class)
        )
        behaviors(InputReceiver)
        facets(Movable, CameraMover)
    }

    fun newWall(): GameEntity<Wall> = newGameEntityOfType(Wall) {
        attributes(EntityPosition(), BlockOccupier, EntityTile(GameTileRepository.WALL))
        facets(Diggable)
    }
}