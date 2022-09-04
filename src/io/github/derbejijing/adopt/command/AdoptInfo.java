package io.github.derbejijing.adopt.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.derbejijing.adopt.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class AdoptInfo implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
        if(sender instanceof Player) {
    		Player player = (Player)sender;

            TextComponent url = new TextComponent(ChatColor.GRAY + "https://github.com/DerBejijing/adopt-plugin");
            url.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/DerBejijing/adopt-plugin"));

            player.sendMessage(ChatColor.GREEN + "-----------------------------------------------");
            player.spigot().sendMessage(url);
            player.sendMessage(ChatColor.GREEN + "-----------------------------------------------");
        } 

		return false;
	}

}
