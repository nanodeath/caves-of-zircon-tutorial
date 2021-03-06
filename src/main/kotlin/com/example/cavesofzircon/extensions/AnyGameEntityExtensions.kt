package com.example.cavesofzircon.extensions

import com.example.cavesofzircon.attributes.EntityPosition
import com.example.cavesofzircon.attributes.EntityTile
import org.hexworks.amethyst.api.Attribute
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