package io.github.derbejijing.adopt.command;

import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import io.github.derbejijing.adopt.Main;
import net.md_5.bungee.api.ChatColor;

public class AdoptDataSave implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		Main.log("user " + sender.getName() + ": /adoptsave " + String.join(" ", args));
		
		if(sender.isOp()) {
			try {
				Main.getData().data_save();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else sender.sendMessage(ChatColor.RED + "You do not have permission to run this command");
		
		Main.log("adoptsave command done");
		
		return false;
	}

}
