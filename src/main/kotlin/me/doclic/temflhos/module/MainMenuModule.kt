package me.doclic.temflhos.module

import me.doclic.temflhos.config.ListConfigType
import me.doclic.temflhos.config.BooleanConfigType
import me.doclic.temflhos.config.ConfigNode
import me.doclic.temflhos.config.StringConfigType
import me.doclic.temflhos.event.SplashTextEvent
import kotlin.random.Random

object MainMenuModule : Module("main_menu", "Main Menu") {
    private val replaceSplashText = ConfigNode("replace_splash_text", true, BooleanConfigType, config)
    private val splashText = ConfigNode("splash_texts", listOf(
        "TEMflHoS!",
        "The Epic Mod for like Hypixel or Sth",
        "Essential free!",
        "... with a hack client",
        "https://nicovideo.jp/watch/sm8628149",
        "eg",
        "this text is so unnecessarily long that it is barely readable even with a 4k monitor, you might even have to just read the source code, in which case, hello",
        "1.8.9!",
        "size 1 fish",
        "in c, strlen(\"一 二 三\") == 11",
        "approved by obama",
        "after 9 years in development",
        "MISSINGNO.",
        "global offensive",
        "source",
        "null",
    ), ListConfigType(StringConfigType), config)

    override fun onSplashText(e: SplashTextEvent) {
        if(replaceSplashText.value) e.splashText = splashText.value[Random.nextInt(splashText.value.size)]
    }
}