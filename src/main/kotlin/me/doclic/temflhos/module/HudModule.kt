package me.doclic.temflhos.module

import me.doclic.temflhos.config.BooleanConfigType
import me.doclic.temflhos.config.ColorConfigType
import me.doclic.temflhos.config.ConfigNode
import me.doclic.temflhos.config.FloatConfigType
import me.doclic.temflhos.config.IntConfigType
import me.doclic.temflhos.util.mc
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.awt.Color
import kotlin.math.floor

object HudModule : Module("hud", "HUD") {
    private val chromaColorCount = ConfigNode("chroma_color_count", 256, IntConfigType, config)
    private val chromaSpeed = ConfigNode("chroma_speed", 1f, FloatConfigType, config)
    private val chroma = ConfigNode("chroma", false, BooleanConfigType, config)
    private val color = ConfigNode("color", Color(0xFF00FF), ColorConfigType, config)
    private val moduleID = ConfigNode("module_id", false, BooleanConfigType, config)
    private val paddingX = ConfigNode("padding_x", 2, IntConfigType, config)
    private val paddingY = ConfigNode("padding_x", 2, IntConfigType, config)
    private val dropShadow = ConfigNode ("drop_shadow", true, BooleanConfigType, config)


    @SubscribeEvent
    fun render(event: RenderGameOverlayEvent.Text) {
        val activeModules = ModuleManager.registry.filter { entry -> entry.value.enabled.value }
        val textColor: Int
        if (chroma.value) {
            // This converts the current time to a hue
            // The mod 10000 is here because of float inaccuracy
            var hue = (chromaSpeed.value * 0.1f * (System.currentTimeMillis() % 10000).toFloat()) / 1000f
            // Limit the amount of colors used
            hue %= 1f
            hue = floor(hue * chromaColorCount.value.toFloat()) / chromaColorCount.value.toFloat()
            textColor = Color.HSBtoRGB(hue, 0.7f, 1f)
        } else textColor = color.value.rgb
        if (moduleID.value) drawMultiLineString(activeModules.keys.joinToString("\n"), paddingX.value, paddingY.value, textColor, dropShadow.value)
        else drawMultiLineString(activeModules.values.joinToString("\n") { module: Module -> module.name }, paddingX.value, paddingY.value, textColor, dropShadow.value)
    }


    private fun drawMultiLineString (str: String, x: Int, y: Int, color: Int, dropShadow: Boolean) {
        var offset: Float = y.toFloat()
        for (string in str.split("\n")) {
            mc.fontRendererObj.drawString(string, x.toFloat(), offset, color, dropShadow)
            offset += mc.fontRendererObj.FONT_HEIGHT
        }
    }

}