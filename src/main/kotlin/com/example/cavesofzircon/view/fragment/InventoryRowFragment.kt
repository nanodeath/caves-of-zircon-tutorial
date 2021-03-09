package com.example.cavesofzircon.view.fragment

import com.example.cavesofzircon.GameConfig
import com.example.cavesofzircon.attributes.Inventory
import com.example.cavesofzircon.attributes.types.CombatItem
import com.example.cavesofzircon.attributes.types.Food
import com.example.cavesofzircon.extensions.GameItem
import com.example.cavesofzircon.extensions.takeIfType
import com.example.cavesofzircon.types.iconTile
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Fragment
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.uievent.Processed

class InventoryRowFragment(width: Int, item: GameItem) : Fragment {
    val dropButton = Components.button()
        .withText("Drop")
        .withDecorations()
        .build()

    val eatButton = Components.button()
        .withText("Eat")
        .withDecorations()
        .build()

    val equipButton = Components.button()
        .withText("Equip")
        .withDecorations()
        .build()

    override val root: Component = Components.hbox()
        .withSpacing(1)
        .withSize(width, 1)
        .build().apply {
            addComponent(Components.icon().withIcon(item.iconTile))
            addComponent(Components.label().withSize(InventoryFragment.NAME_COLUMN_WIDTH, 1).withText(item.name))
            addComponent(dropButton)
            item.takeIfType<Food>()?.run {
                addComponent(eatButton)
            }
            item.takeIfType<CombatItem>()?.run {
                addComponent(equipButton)
            }
        }
}

class InventoryFragment(
    inventory: Inventory,
    width: Int,
    private val onDrop: (GameItem) -> Unit,
    private val onEat: (GameItem) -> Unit,
    private val onEquip: (GameItem) -> GameItem?
) : Fragment {
    override val root: Component = Components.vbox()
        .withSize(width, inventory.size + 1)
        .withColorTheme(GameConfig.THEME)
        .build().apply {
            addComponent(Components.hbox()
                .withSpacing(1)
                .withSize(width, 1)
                .build().apply {
                    addComponent(Components.label().withText("").withSize(1, 1))
                    addComponent(Components.header().withText("Name").withSize(NAME_COLUMN_WIDTH, 1))
                    addComponent(Components.header().withText("Actions").withSize(ACTIONS_COLUMN_WIDTH, 1))
                })

            for (item in inventory.items) {
                addRow(width, item)
            }
        }

    private fun VBox.addRow(
        width: Int,
        item: GameItem
    ) {
        val inventoryRowFragment = InventoryRowFragment(width, item)
        val attachedComponent = addFragment(inventoryRowFragment)
        inventoryRowFragment.apply {
            dropButton.onActivated {
                attachedComponent.detach()
                onDrop(item)
                Processed
            }
            eatButton.onActivated {
                attachedComponent.detach()
                onEat(item)
                Processed
            }
            equipButton.onActivated {
                onEquip(item)?.let { oldItem ->
                    attachedComponent.detach()
                    addRow(width, oldItem)
                }
                Processed
            }
        }
    }

    companion object {
        const val NAME_COLUMN_WIDTH = 15
        const val ACTIONS_COLUMN_WIDTH = 10
    }
}
