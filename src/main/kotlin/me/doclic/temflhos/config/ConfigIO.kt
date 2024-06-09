package me.doclic.temflhos.config

import com.google.common.io.Files
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import me.doclic.temflhos.TEMflHoS
import me.doclic.temflhos.module.ModuleManager
import me.doclic.temflhos.util.mc
import java.io.File
import java.io.FileReader

object ConfigIO {
    private val configFile = File(mc.mcDataDir, "config${File.separator}${TEMflHoS.MODID}.json")
    private val rootDir: ConfigDir = ConfigDir()
    private val modulesDir: ConfigDir = ConfigDir()

    init {
        rootDir.dirs["modules"] = modulesDir
        for(moduleName in ModuleManager.registry.keys) modulesDir.dirs[moduleName] = ModuleManager.registry[moduleName]!!.config
    }

    fun writeConfig() {
        if(!configFile.parentFile.exists()) configFile.parentFile.mkdirs()
        if(!configFile.exists()) configFile.createNewFile()
        val obj = rootDir.read()
        val json = GsonBuilder().setPrettyPrinting().create().toJson(obj)
        @Suppress("UnstableApiUsage")
        Files.write(json, configFile, Charsets.UTF_8)
    }

    fun reloadConfig() {
        if(!configFile.exists()) writeConfig()
        var reader = FileReader(configFile)
        var elem = JsonParser().parse(reader)

        if(elem !is JsonObject) {
            @Suppress("UnstableApiUsage")
            Files.move(configFile, File(mc.mcDataDir, "config${File.separator}${TEMflHoS.MODID}${System.currentTimeMillis()}.json.bak"))
            writeConfig()
            reader = FileReader(configFile)
            elem = JsonParser().parse(reader)
        }
        if(elem !is JsonObject) throw RuntimeException("Malfunctioning writeConfig()")

        rootDir.update(elem)

        reader.close()
    }
}