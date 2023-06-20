package me.doclic.temflhos.module

import me.doclic.temflhos.config.BooleanConfigType
import me.doclic.temflhos.config.ConfigDir
import me.doclic.temflhos.config.ConfigNode
import me.doclic.temflhos.event.Listener
import me.doclic.temflhos.event.ListenerManager

abstract class Module(val id: String, val name: String) : Listener {
    open val disableOnDisconnect: Boolean
        get() = false
    val config = ConfigDir()
    val enabled = ConfigNode("enabled", false, BooleanConfigType, config,
        onChange = { old, new ->
            run {
                if (old == new) new
                if (new) {
                    onEnable()
                    ListenerManager.register(this)
                } else {
                    ListenerManager.unregister(this)
                    onDisable()
                }
                new
            }
        })

    open fun onEnable() { }
    open fun onDisable() { }
}