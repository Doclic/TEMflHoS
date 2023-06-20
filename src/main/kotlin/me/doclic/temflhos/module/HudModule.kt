package me.doclic.temflhos.module

import me.doclic.temflhos.util.getRGBColor
import me.doclic.temflhos.util.mc
import net.minecraft.client.Minecraft
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import kotlin.math.floor
import kotlin.math.roundToInt

class HudModule : Module("hud", "Hud") {
    override fun onEnable()  {}
    override fun onDisable() {}
    private var time: Float = 0f;
    private val resolution = 256
    private val speed: Float = 25f
    @SubscribeEvent
    fun render(event: RenderGameOverlayEvent.Post) {
        drawMultiLineString(ModuleManager.getActiveModules().values.joinToString("\n") { module -> module.name }, 0, 0, getRGBColor(floor(time.toDouble()).roundToInt(), resolution))
        time = (time + speed/Minecraft.getDebugFPS())%resolution
    }


    private fun drawMultiLineString (str: String, x: Int, y: Int, color: Int) {
        var offset = y
        for (string in str.split("\n")) {
            mc.fontRendererObj.drawString(string, x, offset, color)
            offset += mc.fontRendererObj.FONT_HEIGHT
        }
    }

}