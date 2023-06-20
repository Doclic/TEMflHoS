package me.doclic.temflhos.config

import com.google.gson.JsonObject


class ConfigDir {
    val nodes: ArrayList<ConfigNode<*>> = ArrayList()
    val dirs: HashMap<String, ConfigDir> = HashMap()

    private fun checkNamingCollisions() {
        val names = HashSet<String>()
        nodes.forEach { node -> run {
            if(names.contains(node.name)) throw NamingCollisionException(node.name)
            names.add(node.name)
        } }
        dirs.keys.forEach { name-> run {
            if(names.contains(name)) throw NamingCollisionException(name)
            names.add(name)
        } }
    }

    fun update(obj: JsonObject) {
        checkNamingCollisions()
        dirs.keys.forEach { key -> run {
            if(!obj.has(key)) return@run
            dirs[key]?.update(obj.get(key) as JsonObject)
        } }
        nodes.forEach { node -> run {
            if(!obj.has(node.name)) return@run
            node.update(obj.get(node.name))
        } }
    }
}

class NamingCollisionException(val name: String, msg: String = "Naming collision: $name") : RuntimeException(msg)