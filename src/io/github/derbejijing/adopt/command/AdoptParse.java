package io.github.derbejijing.adopt.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.derbejijing.adopt.Main;
import net.md_5.bungee.api.ChatColor;

public class AdoptParse implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		Main.log("user " + sender.getName() + ": /adoptparse " + String.join(" ", args));
		
		boolean permission = false;
		
		if(sender instanceof Player) {
			Player p = (Player)sender;
			for(String s : p.getScoreboardTags()) if(s == "Moderator") permission = true;
		}
		if(sender.hasPermission("*")) permission = true;
		
		if(permission) {
			String instruction = String.join(" ", args);
			
			if(!Main.getData().data_parse(instruction)) {
				Main.log("invalid instruction");
				sender.sendMessage(ChatColor.RED + "invalid instruction");
			}
			
		}
		
		Main.log("adoptparse command done");
		
		return false;
	}

}
