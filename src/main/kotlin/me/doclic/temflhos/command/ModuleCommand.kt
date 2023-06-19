package me.doclic.temflhos.command

import me.doclic.temflhos.module.Module
import me.doclic.temflhos.module.ModuleManager
import me.doclic.temflhos.util.tChat
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender

class ModuleCommand : CommandBase() {
    override fun getCommandName(): String = "module"
    override fun canCommandSenderUseCommand(sender: ICommandSender?): Boolean = true
    override fun getCommandUsage(sender: ICommandSender?): String = "$commandName <${actions.joinToString(" | ")} | {module name} (runs toggle)> [...]"
    private fun toggleModule (module :Module) {
        module.enabled = !module.enabled
        tChat("${if(module.enabled) "Enabled" else "Disabled"} module ${module.name}")
    }
    private val actions = listOf("toggle", "enable", "disable", "state", "list")
    private fun isValidAction(argument: String) = actions.contains(argument)
    private fun handleModulelessCommand (action: String) {
        when (action) {
            "list" -> listModules()
        }
    }
    private fun listModules() {
        tChat(ModuleManager.modules.keys.toString())
    }
    override fun processCommand(sender: ICommandSender, args: Array<out String>) {
        if(args.isEmpty()) {
            tChat("Usage: /${getCommandUsage(sender)}")
            return
        }
        val action = args[0].lowercase()
        val module : Module? = ModuleManager.getModule(if (isValidAction(action) && args.size > 1) args[1] else args[0])
        if(module == null) {
            if (isValidAction(action)) {
                handleModulelessCommand(action)
                return
            }
            tChat("Unknown module ${if (args.size == 1) args[0] else args[1]}")
            return
        }
        when (action) {
            "toggle" -> {
                toggleModule(module)
            }
            "enable" -> {
                if(module.enabled) tChat("Module ${module.name} is already enabled")
                else {
                    module.enabled = true
                    tChat("Enabled module ${module.name}")
                }
            }
            "disable" -> {
                if(!module.enabled) tChat("Module ${module.name} is already disabled")
                else {
                    module.enabled = false
                    tChat("Disabled module ${module.name}")
                }
            }
            "state" -> {
                tChat("Module ${module.name} is ${if(module.enabled) "enabled" else "disabled"}")
            }
            "list" -> {
                listModules()
            }
            else -> {
                //action treated as module name
                toggleModule(module)
            }
        }
    }
}