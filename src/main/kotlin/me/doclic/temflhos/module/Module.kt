package me.doclic.temflhos.module

import me.doclic.temflhos.config.*
import me.doclic.temflhos.event.Listener
import me.doclic.temflhos.event.ListenerManager
import me.doclic.temflhos.util.localPlayer
import me.doclic.temflhos.util.tChat
import org.lwjgl.input.Keyboard

abstract class Module(
    val id: String,
    val name: String,

    val resetOnDisconnect: Boolean = false,
    val enabledByDefault: Boolean = false,
    keyCode: Int = Keyboard.KEY_NONE,
) : Listener {
    val config = ConfigDir()
    val enabled = ConfigNode("enabled", enabledByDefault, BooleanConfigType, config,
        onChange = { old, new ->
            run {
                if (old == new) return@run new
                if (checkCancelled(new)) return@run old
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
    val key = ConfigNode("key", keyCode, IntConfigType, config)
    open fun checkCancelled(enabling: Boolean ): Boolean = false

    open fun onEnable() { }
    open fun onDisable() { }

    open fun sendStateUpdateMsg() {
        tChat("${if (enabled.value) "Enabled" else "Disabled"} module $name")
        localPlayer.playSound("random.click", 1f, 1.1f)
    }
}