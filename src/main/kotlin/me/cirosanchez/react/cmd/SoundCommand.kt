package me.cirosanchez.react.cmd

import me.cirosanchez.react.React
import org.bukkit.Sound
import revxrsal.commands.annotation.AutoComplete
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Default
import revxrsal.commands.annotation.Description
import revxrsal.commands.bukkit.annotation.CommandPermission
import revxrsal.commands.bukkit.player
import revxrsal.commands.command.CommandActor

class SoundCommand(val plugin: React) {

    @Command("sound")
    @CommandPermission("react.commands.sound")
    @Description("Reproduces a sound to the executor")
    @AutoComplete("")
    fun sound(
        actor: CommandActor,
        @Default("ENTITY_CAT_PURREOW") sound: Sound
    ){
        actor.player.playSound(net.kyori.adventure.sound.Sound.sound(sound, net.kyori.adventure.sound.Sound.Source.MASTER, 1f, 1f))
    }
}