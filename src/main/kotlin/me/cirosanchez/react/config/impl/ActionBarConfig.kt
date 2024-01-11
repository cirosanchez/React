package me.cirosanchez.react.config.impl

import me.cirosanchez.react.config.Configuration

class ActionBarConfig : Configuration() {
    override fun getFileName(): String{
        return "actionbar.yml"
    }
}