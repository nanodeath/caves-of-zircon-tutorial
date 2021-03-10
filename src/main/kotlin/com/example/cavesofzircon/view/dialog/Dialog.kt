package com.example.cavesofzircon.view.dialog

import org.hexworks.zircon.api.builder.component.ModalBuilder
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.modal.ModalFragment
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.component.modal.EmptyModalResult

abstract class Dialog(
    private val screen: Screen,
    withClose: Boolean = true
) : ModalFragment<EmptyModalResult> {
    abstract val container: Container

    final override val root by lazy {
        ModalBuilder.newBuilder<EmptyModalResult>()
            .withComponent(container)
            .withParentSize(screen.size)
            .withCenteredDialog(true)
            .build().also {
                if (withClose) {
                    container.addFragment(CloseButtonFragment(it, container))
                }
            }
    }
}
