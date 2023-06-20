package me.doclic.temflhos.config

import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.google.gson.JsonParser
import me.doclic.temflhos.TEMflHoS
import me.doclic.temflhos.module.ModuleManager
import me.doclic.temflhos.util.mc
import java.io.File
import java.io.FileReader

object ConfigIO {
    private val configFile = File(mc.mcDataDir, "config${File.separator}${TEMflHoS.MODID}.json")
    private val rootDir: ConfigDir
    private val modulesDir: ConfigDir
    init {
        rootDir = ConfigDir()
        modulesDir = ConfigDir()
        rootDir.dirs["modules"] = modulesDir
        for(moduleName in ModuleManager.modules.keys) modulesDir.dirs[moduleName] = ModuleManager.getModule(moduleName)!!.config
    }

    fun writeConfig() {
        if(!configFile.parentFile.exists()) configFile.parentFile.mkdirs()
        if(!configFile.exists()) configFile.createNewFile()
    }

    fun reloadConfig() {
        if(!configFile.exists()) return

        val reader = FileReader(configFile)
        val elem = JsonParser().parse(reader)
        if(elem !is JsonObject) throw JsonParseException("Root element wasn't a JsonObject")

        rootDir.update(elem)

        reader.close()
    }
}