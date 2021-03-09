package com.example.cavesofzircon.extensions

import com.example.cavesofzircon.attributes.*
import com.example.cavesofzircon.attributes.flags.BlockOccupier
import com.example.cavesofzircon.attributes.flags.VisionBlocker
import com.example.cavesofzircon.attributes.types.Combatant
import com.example.cavesofzircon.attributes.types.combatStats
import com.example.cavesofzircon.types.Player
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.Attribute
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.zircon.api.data.Tile
import kotlin.reflect.KClass

var AnyGameEntity.position
    get() = requiredAttribute<EntityPosition>().position
    set(value) {
        findAttributeOrNull(EntityPosition::class)?.position = value
    }

val AnyGameEntity.tile: Tile
    get() = requiredAttribute<EntityTile>().tile

inline fun <reified T : Attribute> AnyGameEntity.requiredAttribute(): T
    = requiredAttribute(T::class)

// Called tryToFindAttribute in the documentation
fun <T : Attribute> AnyGameEntity.requiredAttribute(klass: KClass<T>): T {
    return findAttributeOrNull(klass)
        ?: throw NoSuchElementException("Entity '$this' has no property with type '${klass.simpleName}'.")
}

inline fun <reified T : Attribute> AnyGameEntity.optionalAttribute(): T? = findAttributeOrNull(T::class)

val AnyGameEntity.occupiesBlock get() = findAttributeOrNull(BlockOccupier::class) != null

suspend fun AnyGameEntity.tryActionsOn(context: GameContext, target: AnyGameEntity): Response {
    findAttributeOrNull(EntityActions::class)?.let { ea ->
        for (action in ea.createActionsFor(context, this, target)) {
            if (target.receiveMessage(action) is Consumed) {
                return Consumed
            }
        }
    }
    return Pass
}

val AnyGameEntity.isPlayer get() = type == Player

val GameEntity<Combatant>.noHealthLeft get() = combatStats.hp <= 0

val AnyGameEntity.blocksVision get() = findAttributeOrNull(VisionBlocker::class) != null

inline fun <reified T : EntityType> Iterable<AnyGameEntity>.filterType(): List<Entity<T, GameContext>> =
    asSequence().filterType<T>().toList()

inline fun <reified T : EntityType> Sequence<AnyGameEntity>.filterType(): Sequence<Entity<T, GameContext>> =
    filter { it.type is T }.filterIsInstance<Entity<T, GameContext>>()

inline fun <reified T: EntityType> AnyGameEntity.takeIfType(): GameEntity<T>? =
    sequenceOf(this).filterType<T>().firstOrNull()

val AnyGameEntity.attackValue: Int
    get() {
        val combat = optionalAttribute<CombatStats>()?.attackValue ?: 0
        val equipment = optionalAttribute<Equipment>()?.attackValue ?: 0
        val item = optionalAttribute<ItemCombatStats>()?.attackValue ?: 0
        return combat + equipment + item
    }

val AnyGameEntity.defenseValue: Int
    get() {
        val combat = optionalAttribute<CombatStats>()?.defenseValue ?: 0
        val equipment = optionalAttribute<Equipment>()?.defenseValue ?: 0
        val item = optionalAttribute<ItemCombatStats>()?.defenseValue ?: 0
        return combat + equipment + item
    }
