package me.cirosanchez.react.cmd

import me.cirosanchez.react.React
import revxrsal.commands.bukkit.BukkitCommandHandler

class CommandManager(val plugin: React) {
    init {
        val handler: BukkitCommandHandler = BukkitCommandHandler.create(plugin)
        handler.register(
            SoundCommand(plugin),
            ActionBarCommand(plugin),
            ReactCommand(plugin)
        )
        handler.registerBrigadier()
    }
}