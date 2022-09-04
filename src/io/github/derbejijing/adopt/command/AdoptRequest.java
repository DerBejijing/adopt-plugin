package io.github.derbejijing.adopt.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.derbejijing.adopt.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class AdoptRequest implements CommandExecutor {
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(sender instanceof Player) {
			
			Player player = (Player)sender;
			
			if(args.length == 2) {
				
				Player request_target = Bukkit.getPlayer(args[0]);
				
				if(request_target != null) {
					
					if(player.getName() == args[1]) player.sendMessage(ChatColor.RED + "You cannot adopt yourself");
					else if(request_target.getName() == args[1]) player.sendMessage(ChatColor.RED + "Players cannot adopt themselves");
					else if(player == request_target) player.sendMessage(ChatColor.RED + "You cannot adopt a player alone :(");
					else if(Main.getData().playerIsRaisingChild(player.getName())) player.sendMessage(ChatColor.RED + "You are already raising a child");
					else {
					
						switch(Main.getData().request_add(player.getName(), request_target.getName(), args[1])) {
							case -1:
								player.sendMessage(ChatColor.RED + "You cannot make multiple requests at the same time");
								break;
							case -2:
								player.sendMessage(ChatColor.RED + "Your target is already owned");
								break;
							case -3:
								player.sendMessage(ChatColor.RED + "You cannot adopt a grownup");
								break;
							case -4:
								player.sendMessage(ChatColor.RED + "Your request partner is underage you weirdo");
								break;
							case -5:
								player.sendMessage(ChatColor.RED + "That is a weird request");
								break;
							case -6:
								player.sendMessage(ChatColor.RED + "That request has already been made");
								break;
							case -7:
								player.sendMessage(ChatColor.RED + "You are underage");
								break;
							case 0:
								player.sendMessage(ChatColor.GREEN + "-----------------------------------------------");
								player.sendMessage(ChatColor.GRAY + "You requested " + ChatColor.GOLD + request_target.getName() + ChatColor.GRAY + " to adopt and raise " + ChatColor.GOLD + args[1]);
								player.sendMessage(ChatColor.GRAY + "Be informed about what it means to adopt a player!");
								player.sendMessage(ChatColor.GRAY + "For more info type /adoptinfo");
								player.sendMessage(ChatColor.GRAY + "Your request will timeout in [" + ChatColor.RED + "2 minutes" + ChatColor.GRAY + "]");
								player.sendMessage(ChatColor.GREEN + "-----------------------------------------------");
								
								
								TextComponent accept_msg = new TextComponent(ChatColor.GREEN + "[accept]");
								accept_msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to accept").create()));
								accept_msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/adoptaccept " + player.getName()));
								
								TextComponent decline_msg = new TextComponent(ChatColor.RED + "[decline]");
								decline_msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to decline").create()));
								decline_msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/adoptdecline " + player.getName()));
								
								accept_msg.addExtra(" ");
								accept_msg.addExtra(decline_msg);
								
								request_target.sendMessage(ChatColor.GREEN + "-----------------------------------------------");
								request_target.sendMessage(ChatColor.GOLD + player.getName() + ChatColor.GRAY + " wants to adopt and raise " + ChatColor.GOLD + args[1] + ChatColor.GRAY + " with you");
								request_target.sendMessage(ChatColor.GRAY + "Be informed about what it means to adopt a player!");
								request_target.sendMessage(ChatColor.GRAY + "For more info type /adoptinfo");
								request_target.spigot().sendMessage(accept_msg);
								request_target.sendMessage(ChatColor.GREEN + "-----------------------------------------------");								
						}
						
					}
					
				} else {
					player.sendMessage(ChatColor.RED + "That player is not online");
				}
				
			} else this.help(player);
			
		}
				
		return false;
	}
	
	public void help(Player player) {
		player.sendMessage(ChatColor.GREEN + "-----------------------------------------------");
		player.sendMessage(ChatColor.GRAY + "Usage: " + ChatColor.GREEN + "/adoptwith " + ChatColor.GRAY + "<player> <player_to_adopt>");
		player.sendMessage(ChatColor.GRAY + "Where   " + ChatColor.GREEN + "player " + ChatColor.GRAY + "is the player to send a request to");
		player.sendMessage(ChatColor.GRAY + "and      " + ChatColor.GREEN + "player_to_adopt " + ChatColor.GRAY + "is the player's name to adopt");
		player.sendMessage(ChatColor.GREEN + "-----------------------------------------------");
		
	}

}
