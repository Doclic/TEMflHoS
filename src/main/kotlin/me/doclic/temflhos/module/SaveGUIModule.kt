package me.doclic.temflhos.module

import me.doclic.temflhos.config.BooleanConfigType
import me.doclic.temflhos.config.ConfigNode
import me.doclic.temflhos.util.*
import net.minecraft.client.gui.GuiScreen
import net.minecraft.util.EnumChatFormatting
import org.lwjgl.input.Keyboard

object SaveGUIModule : Module("save_gui", "GUI Saved", keyCode = Keyboard.KEY_S, resetOnDisconnect = false) {
    private var savedGUI : GuiScreen? = null
    private val closeOnEnable = ConfigNode("close_on_enable", true, BooleanConfigType, config)
    override fun checkCancelled(enabling: Boolean): Boolean {
        if (!enabling) return false
        if (mc.currentScreen != null) return false
        tChat("${EnumChatFormatting.RED}You need to be inside a GUI in order to save it")
        return true
    }

    override fun onEnable() {
        savedGUI = mc.currentScreen
        if (closeOnEnable.value) mc.displayGuiScreen(null)
    }

    override fun onDisable() {
        if (savedGUI == null) {
            tChat("${EnumChatFormatting.RED}Couldn't find a saved GUI")
            return
        }
        mc.displayGuiScreen(savedGUI)

        savedGUI = null
    }
}