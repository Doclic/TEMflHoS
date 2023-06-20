package me.doclic.temflhos.util

import me.doclic.temflhos.TEMflHoS
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.IChatComponent

fun EntityPlayerSP.tChat(msg: String) {
    chat("${EnumChatFormatting.LIGHT_PURPLE}${EnumChatFormatting.BOLD}${TEMflHoS.NAME}${EnumChatFormatting.WHITE} >>${EnumChatFormatting.DARK_PURPLE} $msg")
}
fun EntityPlayerSP.chat(msg: String) { addChatMessage(IChatComponent.Serializer.jsonToComponent("{\"text\":\"$msg\"}")) }
fun tChat(vararg msgs: String) {
    val player = localPlayer
    for(msg in msgs) player.tChat(msg)
}
fun chat(vararg msgs: String) {
    val player = localPlayer
    for(msg in msgs) player.chat(msg)
}
