package com.example.cavesofzircon.builders

import com.example.cavesofzircon.blocks.GameBlock

object GameBlockFactory {
    fun floor() = GameBlock().also { it.addEntity(EntityFactory.fogOfWar) }
    fun wall() = GameBlock.createWith(EntityFactory.newWall())
    fun stairsDown() = GameBlock.createWith(EntityFactory.newStairsDown())
    fun stairsUp() = GameBlock.createWith(EntityFactory.newStairsUp())
}
