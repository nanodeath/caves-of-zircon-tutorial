package com.example.cavesofzircon.attributes

import com.example.cavesofzircon.GameConfig
import com.example.cavesofzircon.builders.EntityFactory
import com.example.cavesofzircon.extensions.AnyGameEntity
import com.example.cavesofzircon.extensions.position
import com.example.cavesofzircon.extensions.requiredAttribute
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.base.BaseAttribute
import org.hexworks.amethyst.api.base.BaseBehavior
import org.hexworks.zircon.api.data.Size3D

data class FungusSpread(
    var spreadCount: Int = 0,
    val maximumSpread: Int = GameConfig.MAXIMUM_FUNGUS_SPREAD
) : BaseAttribute()

object FungusGrowth : BaseBehavior<GameContext>(FungusSpread::class) {
    override suspend fun update(entity: AnyGameEntity, context: GameContext): Boolean {
        val world = context.world
        val fungusSpread = entity.requiredAttribute<FungusSpread>()
        val (spreadCount, maxSpread) = fungusSpread
        return if (spreadCount < maxSpread && world.random.nextFloat() < 0.015F) {
            world.findEmptyLocationWithin(
                offset = entity.position
                    .withRelativeX(-1)
                    .withRelativeY(-1),
                size = Size3D.create(3, 3, 0)
            )?.let { emptyLocation ->
                world.addEntity(EntityFactory.newFungus(fungusSpread), emptyLocation)
                fungusSpread.spreadCount++
            }
            true
        } else false
    }
}