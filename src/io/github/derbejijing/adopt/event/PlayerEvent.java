package io.github.derbejijing.adopt.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import io.github.derbejijing.adopt.Main;
import net.md_5.bungee.api.ChatColor;

public class PlayerEvent implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		
		Player player = event.getPlayer();
		String name = player.getName();
		
		Main.log("player join " + name);
		
		if(Main.getData().playerIsBeingCelebrated(name)) {
			Main.getData().bossbar_reenable(name);
			Main.log("bossbar reenabled");
		}
			
		if(Main.getData().playerIsProperty(name)) {
			Main.log("player is property");
			
			if(Main.getData().playerIsBeingRaised(name)) {
				Main.getData().bossbar_reenable(name);
				Main.log("bossbar reenabled");
			} else {
				Main.getData().crp_add(name);
				Main.log("first join, child raise phase added");
			}
			
			player.sendMessage(ChatColor.GREEN + "-----------------------------------------------");
			player.sendMessage(ChatColor.GRAY + "You are adopted by " + ChatColor.GOLD + Main.getData().getParrent_1(name) + ChatColor.GRAY + " and " + ChatColor.GOLD + Main.getData().getParrent_2(name) + ChatColor.GRAY + "!");
			player.sendMessage(ChatColor.GRAY + "This makes you " + ChatColor.RED + "their property" + ChatColor.GRAY + "!");
			player.sendMessage(ChatColor.GRAY + "You will be grownup on [" + ChatColor.RED + Main.getData().grownup_getDate(name) + ChatColor.GRAY + "]");
			player.sendMessage(ChatColor.GREEN + "-----------------------------------------------");	
				
				
		}
		
		Main.log("player join done");
		
	}
	
}
