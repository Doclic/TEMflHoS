package me.doclic.temflhos.command

import me.doclic.temflhos.config.ConfigIO
import me.doclic.temflhos.util.tChat
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender

object ConfigCommand : CommandBase() {
    override fun getCommandName(): String = "config"
    override fun canCommandSenderUseCommand(sender: ICommandSender?): Boolean = true
    private val commandUsage: String = "$commandName reload"
    override fun getCommandUsage(sender: ICommandSender?): String = commandUsage
    override fun processCommand(sender: ICommandSender, args: Array<out String>) {
        if (args.isEmpty()) {
            tChat("Usage: /${getCommandUsage(sender)}")
            return
        }

        if(!args[0].equals("reload", true)) return

        ConfigIO.reloadConfig()

        tChat("Reloaded the config")
    }
}