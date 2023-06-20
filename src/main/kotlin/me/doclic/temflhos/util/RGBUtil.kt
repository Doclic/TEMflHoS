package me.doclic.temflhos.util

import java.awt.Color

fun getRGBColor (color: Int, resolution: Int, saturation: Float = 0.7f, brightness: Float = 1f): Int {
    if (resolution > 256) throw UnsupportedOperationException("Resolution can't be higher than 256")
    if (256%resolution != 0) throw UnsupportedOperationException("Resolution must be a divisor of 256")
    if (color >= resolution) throw UnsupportedOperationException("Color value must be strictly lower than the resolution")
    val hue: Float = ((256/resolution) * color) / 256f
    return Color.HSBtoRGB(hue, saturation, brightness)
}