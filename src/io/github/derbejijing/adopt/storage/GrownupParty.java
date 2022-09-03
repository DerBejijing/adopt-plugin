package io.github.derbejijing.adopt.storage;

import java.util.Calendar;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import io.github.derbejijing.adopt.Main;

public class GrownupParty {
	
	String playername;
	Date end;
	long last_event;
	long event_delay;

	public GrownupParty(String playername) {
		this.playername = playername;
		Date date_now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date_now);
		calendar.add(Calendar.SECOND, 10);
		this.end = calendar.getTime();
		this.last_event = 0;
		this.event_delay = 500;
		
		Main.getData().bossbar_setTitle(playername, "YOU ARE NO LONGER PROPERTY!!");
		Main.log("construct party: " + playername + " " + date_now.getTime() + " " + end.getTime());
	}
	
	public void tick() {
		Date now = new Date();
		
		if(now.getTime() > this.last_event + this.event_delay) {
			Main.getData().bossbar_toggleColor(playername);
			
			Player player = Bukkit.getPlayer(playername);
			if(player != null) {
				Location location = player.getLocation();

				Firework firework = (Firework) player.getWorld().spawnEntity(location, EntityType.FIREWORK);
				FireworkMeta firework_meta = firework.getFireworkMeta();
				
				firework_meta.setPower(0);
				firework_meta.addEffect(FireworkEffect.builder().withColor(Color.LIME).flicker(true).build());
				
				firework.setFireworkMeta(firework_meta);
				firework.detonate();
			}
			
			this.last_event = now.getTime();
		}
		
	}
	
	public boolean over() {
		if(new Date().compareTo(this.end) > 0) return true;
		return false;
	}
	
}
