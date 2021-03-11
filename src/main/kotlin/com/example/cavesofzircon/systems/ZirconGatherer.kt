package com.example.cavesofzircon.systems

import com.example.cavesofzircon.attributes.ZirconCounter
import com.example.cavesofzircon.attributes.types.ZirconHolder
import com.example.cavesofzircon.attributes.types.zirconCounter
import com.example.cavesofzircon.commands.PickItemUp
import com.example.cavesofzircon.events.logGameEvent
import com.example.cavesofzircon.extensions.position
import com.example.cavesofzircon.extensions.takeIfType
import com.example.cavesofzircon.types.Zircon
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet

object ZirconGatherer : BaseFacet<GameContext, PickItemUp>(PickItemUp::class, ZirconCounter::class) {
    override suspend fun receive(message: PickItemUp): Response {
        val (context, source) = message
        val world = context.world
        val position = source.position
        world.findTopItem(position)?.let { item ->
            source.takeIfType<ZirconHolder>()?.let { zirconHolder ->
                if (item.type == Zircon) {
                    zirconHolder.zirconCounter.zirconCount++
                    world.removeEntity(item)
                    logGameEvent("$zirconHolder picked up a Zircon!", source)
                    return Consumed
                }
            }
        }
        return Pass
    }
}
