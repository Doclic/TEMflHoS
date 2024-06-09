package me.doclic.temflhos.config

import com.google.gson.JsonElement

class ConfigNode<T>(
    val name: String,
    defaultValue: T,
    val type: ConfigType<T>,
    val dir: ConfigDir,
    val saved: Boolean = true,
    val onChange: (old: T, new: T) -> T = { _, new -> new }
) {
    var value: T = defaultValue
        set(value) { field = onChange(field, value); ConfigIO.writeConfig() }

    init {
        dir.nodes += this
    }

    fun update(elem: JsonElement) {
        value = type.fromElement(elem)
    }

    fun read():JsonElement {
        return type.toElement(value)
    }
}