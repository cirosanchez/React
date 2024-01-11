package me.cirosanchez.react.util

import org.bukkit.Bukkit

class Logger {
    companion object{
        fun log(msg: String){
            Bukkit.getConsoleSender().sendMessage(CC.colorize(msg))
        }
    }
}