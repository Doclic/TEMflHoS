package me.doclic.temflhos.module

import me.doclic.temflhos.config.ListConfigType
import me.doclic.temflhos.config.BooleanConfigType
import me.doclic.temflhos.config.ConfigNode
import me.doclic.temflhos.config.StringConfigType
import me.doclic.temflhos.event.SplashTextEvent
import kotlin.random.Random

object MainMenuModule : Module("main_menu", "Main Menu", enabledByDefault = true) {
    private val replaceSplashText = ConfigNode("replace_splash_text", true, BooleanConfigType, config)
    private val splashText = ConfigNode("splash_texts", listOf("TEMflHoS!"), ListConfigType(StringConfigType), config)

    override fun onSplashText(e: SplashTextEvent) {
        if(replaceSplashText.value) e.splashText = splashText.value[Random.nextInt(splashText.value.size)]
    }
}