package me.doclic.temflhos.event

import java.util.Collections

object ListenerManager {
    private val writableListeners = HashSet<Listener>()

    fun registerListener(listener: Listener) {
        if(hasListener(listener)) return

        writableListeners.add(listener)
    }
    fun hasListener(listener: Listener): Boolean { return writableListeners.contains(listener) }
    fun removeListener(listener: Listener) { writableListeners.remove(listener) }
    val listeners: Set<Listener> get() = Collections.unmodifiableSet(writableListeners)
}