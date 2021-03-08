package com.example.cavesofzircon.attributes

import com.example.cavesofzircon.extensions.GameItem
import org.hexworks.amethyst.api.base.BaseAttribute
import org.hexworks.cobalt.core.api.UUID

class Inventory(val size: Int) : BaseAttribute() {
    private val currentItems = mutableListOf<GameItem>()

    val items: List<GameItem> get() = currentItems
    val isEmpty get() = currentItems.isEmpty()
    val isFull get() = currentItems.size >= size

    fun findItemBy(id: UUID): GameItem? = items.firstOrNull { it.id == id }
    fun addItem(item: GameItem) = if (!isFull) currentItems.add(item) else false
    fun removeItem(item: GameItem) = currentItems.remove(item)
}
