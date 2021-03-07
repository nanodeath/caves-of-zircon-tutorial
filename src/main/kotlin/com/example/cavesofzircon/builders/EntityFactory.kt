package com.example.cavesofzircon.builders

import com.example.cavesofzircon.attributes.*
import com.example.cavesofzircon.builders.GameTileRepository.PLAYER
import com.example.cavesofzircon.commands.Attack
import com.example.cavesofzircon.commands.Dig
import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.flags.BlockOccupier
import com.example.cavesofzircon.systems.*
import com.example.cavesofzircon.types.Fungus
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
            EntityActions(Dig, Attack)
        )
        behaviors(InputReceiver)
        facets(Movable, CameraMover)
    }

    fun newWall(): GameEntity<Wall> = newGameEntityOfType(Wall) {
        attributes(EntityPosition(), BlockOccupier, EntityTile(GameTileRepository.WALL))
        facets(Diggable)
    }

    fun newFungus(fungusSpread: FungusSpread = FungusSpread()) = newGameEntityOfType(Fungus) {
        attributes(
            BlockOccupier,
            EntityPosition(),
            EntityTile(GameTileRepository.FUNGUS),
            fungusSpread
        )
        behaviors(FungusGrowth)
        facets(Attackable)
    }
}