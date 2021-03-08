package com.example.cavesofzircon.systems

import com.example.cavesofzircon.GameConfig
import com.example.cavesofzircon.attributes.types.EnergyUser
import com.example.cavesofzircon.attributes.types.Food
import com.example.cavesofzircon.commands.DropItem
import com.example.cavesofzircon.commands.Eat
import com.example.cavesofzircon.commands.InspectInventory
import com.example.cavesofzircon.extensions.takeIfType
import com.example.cavesofzircon.types.inventory
import com.example.cavesofzircon.view.fragment.InventoryFragment
import com.example.cavesofzircon.world.GameContext
import kotlinx.coroutines.runBlocking
import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.builder.component.ModalBuilder
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.internal.component.modal.EmptyModalResult

object InventoryInspector : BaseFacet<GameContext, InspectInventory>(InspectInventory::class) {
    val DIALOG_SIZE = Size.create(33, 15)

    override suspend fun receive(message: InspectInventory): Response {
        val (context, itemHolder) = message
        val screen = context.screen
        val panel = Components.panel()
            .withSize(DIALOG_SIZE)
            .withDecorations(box(title = "Inventory"), shadow())
            .withAlignmentWithin(context.screen, ComponentAlignment.CENTER)
            .build()

        val fragment = InventoryFragment(itemHolder.inventory, DIALOG_SIZE.width - 3, onDrop = { item ->
            runBlocking {
                itemHolder.receiveMessage(DropItem(context, itemHolder, item))
            }
        }, onEat = { item ->
            runBlocking {
                itemHolder.takeIfType<EnergyUser>()?.let { eater ->
                    item.takeIfType<Food>()?.let { food ->
                        itemHolder.receiveMessage(Eat(context, eater, food))
                    }
                }
            }
        })
        panel.addFragment(fragment)

        val modal = ModalBuilder.newBuilder<EmptyModalResult>()
            .withParentSize(screen.size)
            .withComponent(panel)
            .withColorTheme(GameConfig.THEME)
            .build()

        panel.addComponent(Components.button()
            .withText("Close")
            .withAlignmentWithin(panel, ComponentAlignment.BOTTOM_LEFT)
            .build().apply {
                onActivated {
                    modal.close(EmptyModalResult)
                    Processed
                }
            }
        )
        screen.openModal(modal)
        return Consumed
    }
}
