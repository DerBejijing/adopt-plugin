package io.github.derbejijing.adopt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import io.github.derbejijing.adopt.command.AdoptAccept;
import io.github.derbejijing.adopt.command.AdoptDataSave;
import io.github.derbejijing.adopt.command.AdoptDecline;
import io.github.derbejijing.adopt.command.AdoptParse;
import io.github.derbejijing.adopt.command.AdoptRequest;
import io.github.derbejijing.adopt.command.ImportPlayer;
import io.github.derbejijing.adopt.event.PlayerEvent;
import io.github.derbejijing.adopt.storage.DataStorage;

public class Main extends JavaPlugin{
	
	private static DataStorage data;
	
	private static File debug_log;
	private static FileWriter debug_writer;
	private static BufferedWriter debug_buffered_writer;
	private static boolean debug;
	
	
	@Override
	public void onEnable() {
		try {
			data = new DataStorage("adopt_data.txt");
			data.data_load();
			data.bossbar_reenable_all();
		} catch(Exception e) {
			e.printStackTrace();
			Bukkit.getServer().shutdown();
		}
			
		this.getCommand("adoptwith").setExecutor(new AdoptRequest());
		this.getCommand("adoptaccept").setExecutor(new AdoptAccept());
		this.getCommand("adoptdecline").setExecutor(new AdoptDecline());
		this.getCommand("importplayer").setExecutor(new ImportPlayer());
		this.getCommand("adoptsave").setExecutor(new AdoptDataSave());
		this.getCommand("adoptparse").setExecutor(new AdoptParse());
		this.getServer().getPluginManager().registerEvents(new PlayerEvent(), this);
		
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				Main.getData().tick();
			}
		}.runTaskTimer(this, 0, 20);
		
		
	}
	
	@Override
	public void onDisable() {
		try {
			data.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static DataStorage getData() {
		try {
			if(data == null) data = new DataStorage("adopt_data.txt");
			return data;
		} catch(Exception e) {
			e.printStackTrace();
			Bukkit.getServer().shutdown();
		}
		return null;
	
	}

	private static void start_debug() throws IOException {
		Main.debug = true;
		Main.debug_log = new File("adopt_debug.txt");
		Main.debug_writer = new FileWriter(Main.debug_log, true);
		Main.debug_buffered_writer = new BufferedWriter(Main.debug_writer);
	}
	
	private static void stop_debug() throws IOException {
		Main.debug_buffered_writer.close();
		Main.debug_writer.close();
	}
	
	public static void log(String text) {
		if(Main.debug) {
			try {
				Main.debug_buffered_writer.write(text + "\n");
			} catch (IOException e) {
				e.printStackTrace();
				Bukkit.getServer().shutdown();
			}
		}
	}
	
}
