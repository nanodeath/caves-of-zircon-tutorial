package com.example.cavesofzircon.view.dialog

import com.example.cavesofzircon.extensions.GameItem
import com.example.cavesofzircon.types.iconTile
import org.hexworks.zircon.api.ComponentDecorations
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.GraphicalTilesetResources
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.screen.Screen

class ExamineDialog(screen: Screen, item: GameItem) : Dialog(screen) {

    override val container = Components.panel()
            .withDecorations(ComponentDecorations.box(BoxType.TOP_BOTTOM_DOUBLE, "Examining ${item.name}"))
            .withSize(30, 15)
            .build().apply {
                addComponent(Components.textBox(28)
                        .addHeader("Name", withNewLine = false)
                        .addInlineComponent(Components.icon()
                                .withIcon(item.iconTile)
                                .withTileset(GraphicalTilesetResources.nethack16x16())
                                .build())
                        .addInlineComponent(Components.label()
                                .withText(" ${item.name}")
                                .build())
                        .commitInlineElements()
                        .addNewLine()
                        .addHeader("Description", withNewLine = false)
                        .addParagraph(item.description, withNewLine = false))
            }
}
