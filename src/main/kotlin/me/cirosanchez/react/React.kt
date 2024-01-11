package me.cirosanchez.react

import me.cirosanchez.react.cmd.CommandManager
import me.cirosanchez.react.config.impl.ActionBarConfig
import me.cirosanchez.react.config.impl.LangConfig
import me.cirosanchez.react.managers.ActionBarManager
import org.bukkit.plugin.java.JavaPlugin

class React : JavaPlugin() {

    companion object{
        lateinit var instance: React
    }

    lateinit var actionBarConfig: ActionBarConfig
    lateinit var actionbar: ActionBarManager
    lateinit var commands: CommandManager
    lateinit var langConfig: LangConfig

    override fun onEnable() {
        instance = this



        config()
        managers()
    }

    override fun onDisable() {

    }



    fun config(){
        saveDefaultConfig()
        actionBarConfig = ActionBarConfig()
        actionBarConfig.loadConfig()
        langConfig = LangConfig()
        langConfig.loadConfig()

    }
    fun managers(){
        actionbar = ActionBarManager(instance)
        commands = CommandManager(instance)
    }

    fun reload(){
        actionBarConfig.reload()
        langConfig.reload()
        actionbar.unload()
        managers()
    }


}