package io.github.derbejijing.adopt.storage;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import io.github.derbejijing.adopt.Main;

public class GrownupTimerBossbar {
	
	public BossBar bossbar;
	public Player player;
	public String playerName;
	public BarColor barcolor;
	
	public GrownupTimerBossbar(String playername) {
		this.player = null;
		this.playerName = playername;
		this.barcolor = BarColor.RED;
		this.bossbar = Bukkit.createBossBar(Main.getData().crp_getGrownupDateFormatted(playerName), this.barcolor, BarStyle.SOLID, BarFlag.DARKEN_SKY);
		this.bossbar.setProgress(0);
		
		Main.log("construct bossbar: " + playername);
	}
	
	
	public String getString() {
		return "BOSSBAR " + playerName;
	}
	
	
	public void enableBossbar() {
		Main.log("bossbar enabled for: " + this.playerName);
		Player player = Bukkit.getPlayer(this.playerName);
		if(player == null) return;
		this.bossbar.addPlayer(player);
	}
	
	
	public void tickBossbar() {
		this.bossbar.setProgress(Main.getData().crp_getProgress(playerName));
	}
	
	
	public void destroy() {
		this.bossbar.setVisible(false);
	}
	
	
	public void setTitle(String title) {
		this.bossbar.setTitle(title);
	}
	
	
	public void toggleColor() {
		if(this.barcolor == BarColor.RED) {
			this.barcolor = BarColor.GREEN;
			this.bossbar.setColor(this.barcolor);
		}
		else if(this.barcolor == BarColor.GREEN) {
			this.barcolor = BarColor.RED;
			this.bossbar.setColor(this.barcolor);
		}
	}

}
