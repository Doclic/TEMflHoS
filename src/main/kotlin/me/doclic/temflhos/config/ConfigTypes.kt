package me.doclic.temflhos.config

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import java.awt.Color

interface ConfigType<T> {
    fun toElement(value: T): JsonElement
    fun fromElement(elem: JsonElement): T
}

object StringConfigType : ConfigType<String> {
    override fun toElement(value: String): JsonElement = JsonPrimitive(value)
    override fun fromElement(elem: JsonElement): String {
        if(elem !is JsonPrimitive || !elem.isString) throw TypeCastException("elem wasn't a String")
        return elem.asString
    }
}

object IntConfigType : ConfigType<Int> {
    override fun toElement(value: Int): JsonElement = JsonPrimitive(value)
    override fun fromElement(elem: JsonElement): Int{
        if(elem !is JsonPrimitive || !elem.isNumber) throw TypeCastException("elem wasn't an Int")
        return elem.asInt
    }
}

object BooleanConfigType : ConfigType<Boolean> {
    override fun toElement(value: Boolean): JsonElement = JsonPrimitive(value)
    override fun fromElement(elem: JsonElement): Boolean{
        if(elem !is JsonPrimitive || !elem.isBoolean) throw TypeCastException("elem wasn't an Boolean")
        return elem.asBoolean
    }
}

object FloatConfigType : ConfigType<Float> {
    override fun toElement(value: Float): JsonElement = JsonPrimitive(value)
    override fun fromElement(elem: JsonElement): Float {
        if(elem !is JsonPrimitive || !elem.isNumber) throw TypeCastException("elem wasn't a Float")
        return elem.asFloat
    }
}

object ColorConfigType : ConfigType<Color> {
    override fun toElement(value: Color): JsonElement = JsonPrimitive("#%06x".format(value.rgb))
    override fun fromElement(elem: JsonElement): Color {
        // FIXME this code sucks
        val str = StringConfigType.fromElement(elem)
        if(!str.startsWith("#")) throw TypeCastException("elem wasn't a Color")
        val num = str.substring(1).toInt(16)
        val alpha = if(str.length > 7) num shr 24
                    else               0xFF
        val col = Color(num)
        return Color(col.red, col.green, col.blue, alpha)
    }
}

class ListConfigType<T>(private val type: ConfigType<T>) : ConfigType<List<T>> {
    override fun toElement(value: List<T>): JsonElement {
        val elem = JsonArray()
        // Making sure it executes in order
        for(i in value.indices) { elem.add(type.toElement(value[i])) }
        return elem
    }

    override fun fromElement(elem: JsonElement): List<T> {
        if(elem !is JsonArray) throw TypeCastException("elem wasn't an Array")
        val list = ArrayList<T>()
        for(i in 0 until elem.size())
            list.add(type.fromElement(elem.get(i)))
        return list
    }
}