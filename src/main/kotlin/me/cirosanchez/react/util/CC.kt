package me.cirosanchez.react.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage

class CC {
    companion object {

        fun colorize(msg: String): Component {
            return MiniMessage.miniMessage().deserialize(msg)
        }
    }
}