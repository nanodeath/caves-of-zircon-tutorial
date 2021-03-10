package com.example.cavesofzircon.view.dialog

import org.hexworks.zircon.api.ComponentDecorations
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Panel
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.screen.Screen

class HelpDialog(screen: Screen) : Dialog(screen) {
    override val container = Components.panel()
        .withSize(44, 27)
        .withDecorations(ComponentDecorations.box(BoxType.TOP_BOTTOM_DOUBLE, title = "Help"))
        .build().apply {
            // TODO the keys here shouldn't be hardcoded, they should refer to a constant
            addComponent(
                Components.textBox(42)
                    .addNewLine()
                    .addHeader("Caves of Zircon")
                    .addParagraph(
                        """
                            Descend to the Caves Of Zircon and collect as many Zircons as you can.
                            Find the exit (+) to win the game. Use what you find to avoid dying.
                        """.trimIndent().replace("\n", " ")
                    )
                    .addNewLine()
            )

            addComponent(
                Components.textBox(20)
                    .withPosition(0, 9)
                    .addHeader("Movement:")
                    .addListItem("wasd: Movement")
                    .addListItem("r: Move up")
                    .addListItem("f: Move down")
            )

            addComponent(
                Components.textBox(40)
                    .withPosition(0, 16)
                    .addHeader("Navigation:")
                    .addListItem("[Tab]: Focus next")
                    .addListItem("[Shift] + [Tab]: Focus previous")
                    .addListItem("[Space]: Activate focused item")
            )

            addListBox("Actions:", listOf("(i)nventory", "(p)ick up", "(h)elp"), Position.create(22, 9))
        }
}

private fun Panel.addListBox(header: String, listItems: List<String>, position: Position, width: Int = listItems.maxOf { it.length }) {
    addComponent(
        Components.textBox(width)
            .withPosition(position.x, position.y)
            .addHeader(header)
            .also { builder ->
                listItems.forEach { i -> builder.addListItem(i) }
            }
    )
}
