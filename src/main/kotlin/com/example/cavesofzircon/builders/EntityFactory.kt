package com.example.cavesofzircon.builders

import com.example.cavesofzircon.attributes.*
import com.example.cavesofzircon.attributes.flags.BlockOccupier
import com.example.cavesofzircon.attributes.flags.VisionBlocker
import com.example.cavesofzircon.attributes.types.Armor
import com.example.cavesofzircon.attributes.types.Weapon
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
import kotlin.random.Random
import org.hexworks.zircon.api.data.Tile as Tiles

fun <T : EntityType> newGameEntityOfType(
    type: T,
    init: EntityBuilder<T, GameContext>.() -> Unit
): GameEntity<T> = newEntityOfType(type, init)

object EntityFactory {
    fun newPlayer(): GameEntity<Player> = newGameEntityOfType(Player) {
        attributes(
            BlockOccupier,
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
            EnergyLevel(1000, 1000),
            Equipment(newClub(), newJacket())
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

    fun newDagger() = newGameEntityOfType(Dagger) {
        attributes(
            ItemIcon(
                Tiles.newBuilder()
                    .withName("Dagger")
                    .withTileset(GraphicalTilesetResources.nethack16x16())
                    .buildGraphicalTile()
            ),
            EntityPosition(),
            ItemCombatStats(
                attackValue = 4,
                combatItemType = "Weapon"
            ),
            EntityTile(GameTileRepository.DAGGER)
        )
    }

    fun newSword() = newGameEntityOfType(Sword) {
        attributes(
            ItemIcon(
                Tiles.newBuilder()
                    .withName("Short sword")
                    .withTileset(GraphicalTilesetResources.nethack16x16())
                    .buildGraphicalTile()
            ),
            EntityPosition(),
            ItemCombatStats(
                attackValue = 6,
                combatItemType = "Weapon"
            ),
            EntityTile(GameTileRepository.SWORD)
        )
    }

    fun newStaff() = newGameEntityOfType(Staff) {
        attributes(
            ItemIcon(
                Tiles.newBuilder()
                    .withName("staff")
                    .withTileset(GraphicalTilesetResources.nethack16x16())
                    .buildGraphicalTile()
            ),
            EntityPosition(),
            ItemCombatStats(
                attackValue = 4,
                defenseValue = 2,
                combatItemType = "Weapon"
            ),
            EntityTile(GameTileRepository.STAFF)
        )
    }

    fun newLightArmor() = newGameEntityOfType(LightArmor) {
        attributes(
            ItemIcon(
                Tiles.newBuilder()
                    .withName("Leather armor")
                    .withTileset(GraphicalTilesetResources.nethack16x16())
                    .buildGraphicalTile()
            ),
            EntityPosition(),
            ItemCombatStats(
                defenseValue = 2,
                combatItemType = "Armor"
            ),
            EntityTile(GameTileRepository.LIGHT_ARMOR)
        )
    }

    fun newMediumArmor() = newGameEntityOfType(MediumArmor) {
        attributes(
            ItemIcon(
                Tiles.newBuilder()
                    .withName("Chain mail")
                    .withTileset(GraphicalTilesetResources.nethack16x16())
                    .buildGraphicalTile()
            ),
            EntityPosition(),
            ItemCombatStats(
                defenseValue = 3,
                combatItemType = "Armor"
            ),
            EntityTile(GameTileRepository.MEDIUM_ARMOR)
        )
    }

    fun newHeavyArmor() = newGameEntityOfType(HeavyArmor) {
        attributes(
            ItemIcon(
                Tiles.newBuilder()
                    .withName("Plate mail")
                    .withTileset(GraphicalTilesetResources.nethack16x16())
                    .buildGraphicalTile()
            ),
            EntityPosition(),
            ItemCombatStats(
                defenseValue = 4,
                combatItemType = "Armor"
            ),
            EntityTile(GameTileRepository.HEAVY_ARMOR)
        )
    }

    fun newClub() = newGameEntityOfType(Club) {
        attributes(
            ItemCombatStats(combatItemType = "Weapon"),
            EntityTile(GameTileRepository.CLUB),
            EntityPosition(),
            ItemIcon(
                Tiles.newBuilder()
                    .withName("Club")
                    .withTileset(GraphicalTilesetResources.nethack16x16())
                    .buildGraphicalTile()
            )
        )
    }

    fun newJacket() = newGameEntityOfType(Jacket) {
        attributes(
            ItemCombatStats(combatItemType = "Armor"),
            EntityTile(GameTileRepository.JACKET),
            EntityPosition(),
            ItemIcon(
                Tiles.newBuilder()
                    .withName("Leather jacket")
                    .withTileset(GraphicalTilesetResources.nethack16x16())
                    .buildGraphicalTile()
            )
        )
    }

    fun newRandomWeapon(r: Random): GameEntity<Weapon> = when (r.nextInt(3)) {
        0 -> newDagger()
        1 -> newSword()
        else -> newStaff()
    }

    fun newRandomArmor(r: Random): GameEntity<Armor> =
        listOf(::newLightArmor, ::newMediumArmor, ::newHeavyArmor).random(r)()

    fun newZombie(r: Random) = newGameEntityOfType(Zombie) {
        attributes(
            BlockOccupier,
            EntityPosition(),
            EntityTile(GameTileRepository.ZOMBIE),
            Vision(10),
            CombatStats.create(
                maxHp = 25,
                attackValue = 8,
                defenseValue = 4
            ),
            Inventory(2).apply {
                addItem(newRandomWeapon(r))
                addItem(newRandomArmor(r))
            },
            EntityActions(Attack)
        )
        facets(Movable, Attackable, ItemDropper, LootDropper, Destructible)
        behaviors(HunterSeeker or Wanderer)
    }
}
