package com.example.cavesofzircon.commands

import com.example.cavesofzircon.extensions.GameItemHolder
import com.example.cavesofzircon.messages.EntityAction
import com.example.cavesofzircon.types.ItemHolder
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.entity.EntityType

data class PickItemUp(
    override val context: GameContext,
    override val source: GameItemHolder
) : EntityAction<ItemHolder, EntityType>
