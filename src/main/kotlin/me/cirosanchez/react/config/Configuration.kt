package me.cirosanchez.react.config

import me.cirosanchez.react.React
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

abstract class Configuration : YamlConfiguration() {
    val file by lazy {  File(React.instance.dataFolder, getFileName()) }

    companion object {
        var config: Configuration? = null
    }

    fun getConfig(): Configuration {
        if (config == null){
            config = this
        }
        return config!!
    }

    fun loadConfig(){
        if (!file.exists()) React.instance.saveResource(file.name, false)

        super.load(file)
    }

    abstract fun getFileName(): String

    fun reload() {
        super.load(file)
    }

    fun save(){
        super.save(file)
    }

}