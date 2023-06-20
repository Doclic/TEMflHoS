package me.doclic.temflhos.command

import me.doclic.temflhos.module.Module
import me.doclic.temflhos.module.ModuleManager
import me.doclic.temflhos.util.tChat
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender

object ModuleCommand : CommandBase() {
    private val actions = HashMap<String, Action>()
    private enum class Action(val needsModule: Boolean, vararg val names: String) {
        TOGGLE(true, "toggle"),
        ENABLE(true, "enable", "en", "on"),
        DISABLE(true, "disable", "dis", "off"),
        STATE(true, "state"),
        LIST(false, "list");

        init {
            if(names.isEmpty()) throw IllegalArgumentException("Action requires at least 1 name")

            names.forEach { name -> actions[name.lowercase()] = this }
        }
    }
    override fun getCommandName(): String = "module"
    override fun canCommandSenderUseCommand(sender: ICommandSender?): Boolean = true
    override fun getCommandUsage(sender: ICommandSender?): String {
        val moduleActionNames = HashSet<String>()
        val noModuleActionNames = HashSet<String>()
        Action.values().forEach {
            action -> run {
                if (action.needsModule) moduleActionNames.add(action.names[0])
                else noModuleActionNames.add(action.names[0])
            }
        }

        return "$commandName <${moduleActionNames.joinToString(" | ")}> <module> OR " +
                "/$commandName <${noModuleActionNames.joinToString(" |")}> OR " +
                "/$commandName <module>"
    }
    override fun processCommand(sender: ICommandSender, args: Array<out String>) {
        if (args.isEmpty()) {
            tChat("Usage: /${getCommandUsage(sender)}")
            return
        }

        val action: Action?
        val module: Module?
        if (args.size == 1) {
            module = ModuleManager.getModule(args[0])
            action = if (module != null) Action.TOGGLE
                     else actions[args[0].lowercase()]
        } else {
            action = actions[args[0].lowercase()]
            module = ModuleManager.getModule(args[1])
        }

        if(action == null) {
            tChat("Unknown action: ${args[0]}")
            tChat("Usage: /${getCommandUsage(sender)}")
            return
        }
        if(action.needsModule && module == null) {
            tChat("Action ${action.names[0]} requires a module as a parameter")
            return
        }

        when (action) {
            Action.TOGGLE -> { module!!; setState(module, !module.enabled.value) }
            Action.ENABLE -> { module!!; setState(module, true) }
            Action.DISABLE -> { module!!; setState(module, false) }
            Action.STATE -> { module!!; tChat("Module ${module.name} is ${if(module.enabled.value) "enabled" else "disabled"}") }
            Action.LIST -> tChat("Modules: ${ModuleManager.moduleNames}")
        }
    }

    private fun setState(module: Module, state: Boolean) {
        if(module.enabled.value == state) {
            tChat("Module ${module.name} is already ${if (state) "enabled" else "disabled"}")
            return
        }

        module.enabled.value = state
        tChat("${if (state) "Enabled" else "Disabled"} module ${module.name}")
    }
}