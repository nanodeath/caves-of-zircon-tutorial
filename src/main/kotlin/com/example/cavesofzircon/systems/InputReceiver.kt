package com.example.cavesofzircon.systems

import com.example.cavesofzircon.commands.InspectInventory
import com.example.cavesofzircon.commands.MoveDown
import com.example.cavesofzircon.commands.MoveUp
import com.example.cavesofzircon.commands.PickItemUp
import com.example.cavesofzircon.extensions.GameEntity
import com.example.cavesofzircon.extensions.position
import com.example.cavesofzircon.messages.MoveTo
import com.example.cavesofzircon.types.Player
import com.example.cavesofzircon.world.GameContext
import org.hexworks.amethyst.api.base.BaseBehavior
import org.hexworks.amethyst.api.entity.Entity
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEvent

object InputReceiver : BaseBehavior<GameContext>() {
    override suspend fun update(entity: Entity<EntityType, GameContext>, context: GameContext): Boolean {
        val (_, _, uiEvent, player) = context
        val currentPos = player.position
        if (uiEvent is KeyboardEvent) {
            return when (uiEvent.code) {
                KeyCode.KEY_W -> player.moveTo(currentPos.withRelativeY(-1), context)
                KeyCode.KEY_A -> player.moveTo(currentPos.withRelativeX(-1), context)
                KeyCode.KEY_S -> player.moveTo(currentPos.withRelativeY(1), context)
                KeyCode.KEY_D -> player.moveTo(currentPos.withRelativeX(1), context)
                KeyCode.KEY_R -> player.moveUp(context)
                KeyCode.KEY_F -> player.moveDown(context)
                KeyCode.KEY_P -> player.pickItemUp(context)
                KeyCode.KEY_I -> player.inspectInventory(context)
                else -> false // currentPos
            }
        }
        return false
    }
}

private suspend fun GameEntity<Player>.pickItemUp(context: GameContext): Boolean {
    receiveMessage(PickItemUp(context, this))
    return true
}

private suspend fun GameEntity<Player>.inspectInventory(context: GameContext): Boolean {
    receiveMessage(InspectInventory(context, this))
    return true
}

private suspend fun GameEntity<Player>.moveTo(newPosition: Position3D, context: GameContext): Boolean {
    receiveMessage(MoveTo(context, this, newPosition))
    return true
}

private suspend fun GameEntity<Player>.moveUp(context: GameContext): Boolean {
    receiveMessage(MoveUp(context, this, this))
    return true
}

private suspend fun GameEntity<Player>.moveDown(context: GameContext): Boolean {
    receiveMessage(MoveDown(context, this, this))
    return true
}
