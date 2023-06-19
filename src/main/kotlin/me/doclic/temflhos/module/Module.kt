package me.doclic.temflhos.module

import me.doclic.temflhos.event.Listener
import me.doclic.temflhos.event.ListenerManager

abstract class Module(val id: String, val name: String) : Listener {
    open val disableOnDisconnect: Boolean
        get() = false
    var enabled: Boolean = false
        set(value) {
            if(field == value) return
            field = value
            if(value) {
                onEnable()
                ListenerManager.registerListener(this)
            } else {
                ListenerManager.removeListener(this)
                onDisable()
            }
        }

    abstract fun onEnable()
    abstract fun onDisable()
}