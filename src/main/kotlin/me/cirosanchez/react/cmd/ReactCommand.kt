package me.cirosanchez.react.cmd

import me.cirosanchez.react.React
import me.cirosanchez.react.util.CC
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.DefaultFor
import revxrsal.commands.annotation.Description
import revxrsal.commands.annotation.Subcommand
import revxrsal.commands.bukkit.BukkitCommandActor
import revxrsal.commands.bukkit.annotation.CommandPermission
import revxrsal.commands.bukkit.sender
import revxrsal.commands.command.CommandActor

@Command("react")
@Description("React's main command")
@CommandPermission("react.commands.react")
class ReactCommand(val plugin: React) {

    @DefaultFor("~")
    fun help(
        actor: CommandActor
    ){
        actor.sender.sendMessage(CC.colorize("<white>Running</white> <gradient:#04ff00:#00FBFF>React</gradient> <white>v0.0.1</white>"))
        actor.sender.sendMessage(CC.colorize("<white>Use <gradient:#04ff00:#00FBFF>/react help</gradient> <white>to see commands!</white>"))
    }

    @Subcommand("reload")
    @CommandPermission("react.commands.react.reload")
    fun reload(actor: BukkitCommandActor){
        val start = System.currentTimeMillis()
        plugin.reload()
        val finish = System.currentTimeMillis()
        val total = (start-finish)*-1

        actor.sender.sendMessage(CC.colorize("<gradient:#04ff00:#00FBFF>React</gradient> <gray>\u00BB</gray> <white>Reloaded configuration in $total ms!</white>"))
    }
}