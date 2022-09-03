package io.github.derbejijing.adopt.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import io.github.derbejijing.adopt.Main;
import net.md_5.bungee.api.ChatColor;

public class ImportPlayer implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		Main.log("user " + sender.getName() + ": /importplayer " + String.join(" ", args));
		
		boolean permission = false;
		
		if(sender instanceof Player) {
			Player p = (Player)sender;
			for(String s : p.getScoreboardTags()) if(s == "Moderator") permission = true;
		}
		if(sender.hasPermission("*")) permission = true;
		
		if(permission) {
			if(args.length != 2) {
				this.help(sender);
				return false;
			}
			
			ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
			
			switch(args[0]) {
				case "child":
					if(!Main.getData().child_add(args[1])) {
						sender.sendMessage(ChatColor.RED + "specified player is not wanted");
						Main.log("importplayer child not wanted");
						break;
					}
					Bukkit.dispatchCommand(console, "whitelist add " + args[1]);
					break;
				case "grownup":
					Bukkit.dispatchCommand(console, "whitelist add " + args[1]);
					Main.getData().grownup_add(args[1]);
					break;
				default:
					sender.sendMessage(ChatColor.RED + "invalid option");
					Main.log("importplayer invalid option");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "no permission");
			Main.log("importplayer no permission");
		}
		
		Main.log("importplayer command done");
		
		return false;
	}
	
	public void help(CommandSender sender) {
		
		sender.sendMessage(ChatColor.GREEN + "-----------------------------------------------");
		sender.sendMessage(ChatColor.GRAY + "Usage: " + ChatColor.GREEN + "/import " + ChatColor.GRAY + "<child/grownup> <player>");
		sender.sendMessage(ChatColor.GRAY + "Where   " + ChatColor.GREEN + "argument 1 " + ChatColor.GRAY + "specifies if the player to import is a grownup or a child");
		sender.sendMessage(ChatColor.GRAY + "and      " + ChatColor.GREEN + "player " + ChatColor.GRAY + "is the player's name to import");
		sender.sendMessage(ChatColor.GREEN + "-----------------------------------------------");
		
	}

}
