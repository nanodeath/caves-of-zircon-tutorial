package com.example.cavesofzircon.builders

import com.example.cavesofzircon.attributes.*
import com.example.cavesofzircon.attributes.flags.BlockOccupier
import com.example.cavesofzircon.attributes.flags.VisionBlocker
import com.example.cavesofzircon.builders.GameTileRepository.PLAYER
import com.example.cavesofzircon.commands.Attack
import com.example.cavesofzircon.commands.Dig
import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.systems.*
import com.example.cavesofzircon.types.*
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.builder.EntityBuilder
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.amethyst.api.newEntityOfType
import org.hexworks.zircon.api.GraphicalTilesetResources
import org.hexworks.zircon.api.data.Tile

fun <T : EntityType> newGameEntityOfType(
    type: T,
    init: EntityBuilder<T, GameContext>.() -> Unit
): GameEntity<T> = newEntityOfType(type, init)

object EntityFactory {
    fun newPlayer(): GameEntity<Player> = newGameEntityOfType(Player) {
        attributes(
            EntityPosition(),
            EntityTile(PLAYER),
            EntityActions(Dig, Attack),
            CombatStats.create(
                maxHp = 100,
                attackValue = 10,
                defenseValue = 5
            ),
            Vision(9),
            Inventory(10),
            EnergyLevel(1000, 1000)
        )
        behaviors(InputReceiver, EnergyExpender)
        facets(
            Movable,
            CameraMover,
            StairClimber,
            StairDescender,
            Attackable,
            Destructible,
            ItemPicker,
            InventoryInspector,
            ItemDropper,
            EnergyExpender,
            DigestiveSystem
        )
    }

    val fogOfWar = newGameEntityOfType(FogOfWarType) {
        attributes(EntityTile(GameTileRepository.UNREVEALED))
    }

    fun newWall(): GameEntity<Wall> = newGameEntityOfType(Wall) {
        attributes(EntityPosition(), BlockOccupier, EntityTile(GameTileRepository.WALL), VisionBlocker)
        facets(Diggable)
    }

    fun newFungus(fungusSpread: FungusSpread = FungusSpread()) = newGameEntityOfType(Fungus) {
        attributes(
            BlockOccupier,
            EntityPosition(),
            EntityTile(GameTileRepository.FUNGUS),
            fungusSpread,
            CombatStats.create(
                maxHp = 10,
                attackValue = 0,
                defenseValue = 0
            )
        )
        behaviors(FungusGrowth)
        facets(Attackable, Destructible)
    }

    fun newBat() = newGameEntityOfType(Bat) {
        attributes(
            BlockOccupier,
            EntityPosition(),
            EntityTile(GameTileRepository.BAT),
            CombatStats.create(
                maxHp = 5,
                attackValue = 2,
                defenseValue = 1
            ),
            EntityActions(Attack),
            Inventory(1).apply {
                addItem(newBatMeat())
            }
        )
        behaviors(Wanderer)
        facets(Movable, Attackable, ItemDropper, LootDropper, Destructible)
    }

    fun newStairsDown() = newGameEntityOfType(StairsDown) {
        attributes(EntityTile(GameTileRepository.STAIRS_DOWN), EntityPosition())
    }

    fun newStairsUp() = newGameEntityOfType(StairsUp) {
        attributes(EntityTile(GameTileRepository.STAIRS_UP), EntityPosition())
    }

    fun newZircon() = newGameEntityOfType(Zircon) {
        attributes(
            ItemIcon(
                Tile.newBuilder()
                    .withName("white gem")
                    .withTileset(GraphicalTilesetResources.nethack16x16())
                    .buildGraphicalTile()
            ),
            EntityPosition(),
            EntityTile(GameTileRepository.ZIRCON)
        )
    }

    fun newBatMeat() = newGameEntityOfType(BatMeat) {
        attributes(
            ItemIcon(
                Tile.newBuilder()
                    .withName("Meatball")
                    .withTileset(GraphicalTilesetResources.nethack16x16())
                    .buildGraphicalTile()
            ),
            NutritionalValue(750),
            EntityPosition(),
            EntityTile(GameTileRepository.BAT_MEAT)
        )

    }
}
