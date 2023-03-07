package me.doclic.temflhos.module

abstract class Module(val id: String, val name: String) {
    open val disableOnDisconnect: Boolean
        get() = false
    var enabled: Boolean = false
        set(value) {
            if(field == value) return
            field = value
            if(value) onEnable()
            else onDisable()
        }

    abstract fun onEnable()
    abstract fun onDisable()
}