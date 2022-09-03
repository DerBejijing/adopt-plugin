package io.github.derbejijing.adopt.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.derbejijing.adopt.Main;
import net.md_5.bungee.api.ChatColor;

public class AdoptDecline implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		Main.log("user " + sender.getName() + ": /adoptdecline");
		
		if(sender instanceof Player) {
			
			Player player = (Player) sender;
			
			if(args.length == 1) {
				
				if(Main.getData().request_exists(args[0], player.getName())) {
					
					Main.getData().request_decline(args[0], player.getName());
					
				} else player.sendMessage(ChatColor.RED + "Request does not exist");
			}
			
		}
		
		Main.log("adoptdecline command done");
		
		return false;
	}
	
}
