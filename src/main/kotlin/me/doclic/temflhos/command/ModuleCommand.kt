package me.doclic.temflhos.command

import me.doclic.temflhos.module.ModuleManager
import me.doclic.temflhos.util.tChat
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender

class ModuleCommand : CommandBase() {
    override fun getCommandName(): String { return "module" }
    override fun canCommandSenderUseCommand(sender: ICommandSender?): Boolean { return true }
    override fun getCommandUsage(sender: ICommandSender?): String { return "$commandName <module> [toggle (def) | enable | disable | state]" }

    override fun processCommand(sender: ICommandSender, args: Array<out String>) {
        if(args.isEmpty()) {
            tChat("Usage: /${getCommandUsage(sender)}")
            return
        }

        val module = ModuleManager.getModule(args[0])
        if(module == null) {
            tChat("Unknown module ${args[0]}")
            return
        }

        when(val action = if(args.size == 1) "toggle" else args[1].toLowerCase()) {
            "toggle" -> {
                module.enabled = !module.enabled
                tChat("${if(module.enabled) "Enabled" else "Disabled"} module ${module.name}")
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
            else -> {
                tChat("Unknown action: $action")
            }
        }
    }
}