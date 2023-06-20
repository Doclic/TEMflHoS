package me.doclic.temflhos.event

import me.doclic.temflhos.util.eventBus
import java.util.Collections

object ListenerManager {
    private val writableRegistry = HashSet<Listener>()

    fun register(listener: Listener) {
        if(writableRegistry.contains(listener)) return

        writableRegistry.add(listener)
        eventBus.register(listener)
    }
    fun unregister(listener: Listener) {
        writableRegistry.remove(listener)
        eventBus.unregister(listener)
    }
    val registry: Set<Listener> get() = Collections.unmodifiableSet(writableRegistry)
}