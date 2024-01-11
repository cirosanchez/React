package me.cirosanchez.react.config.impl

import me.cirosanchez.react.config.Configuration

class LangConfig : Configuration() {
    override fun getFileName(): String {
        return "lang.yml"
    }
}