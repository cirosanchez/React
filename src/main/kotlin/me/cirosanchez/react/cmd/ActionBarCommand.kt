package me.cirosanchez.react.cmd

import me.cirosanchez.react.React
import me.cirosanchez.react.util.CC
import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.entity.Player
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Description
import revxrsal.commands.annotation.Range
import revxrsal.commands.bukkit.annotation.CommandPermission
import revxrsal.commands.bukkit.player
import revxrsal.commands.bukkit.sender
import revxrsal.commands.command.CommandActor

class ActionBarCommand(val plugin: React) {

    @Command("actionbar")
    @CommandPermission("react.commands.actionbar")
    @Description("Send an actionbar to a player!")
    fun actionbar(
        actor: CommandActor,
        target: Player,
        @Range(min = 1.0) id: Int
    ) {
        if (plugin.actionbar.check(id)){
            plugin.actionbar.send(target, id)
            var msg: String = plugin.langConfig.getString("commands.actionbar.success").toString().replace("{player.name}", target.name)
            actor.sender.sendMessage(CC.colorize(PlaceholderAPI.setPlaceholders(actor.player, msg)))
        } else {
            actor.sender.sendMessage(CC.colorize(PlaceholderAPI.setPlaceholders(actor.player, plugin.langConfig.getString("commands.actionbar.failed").toString())))
        }
    }
}