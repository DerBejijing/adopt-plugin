package io.github.derbejijing.adopt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;

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
	
	private static File importedPlayers;
	private static FileWriter importedPlayersFw;

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

	private static void startImportPlayerLog() {
		try {
			Main.importedPlayers = new File("adopt_import.txt");
			Main.importedPlayersFw = new FileWriter(Main.importedPlayers);
		catch(IOException e) {
			e.printStackTrace();
			Bukkit.getServer().shutdown();
		}
	}

	private static void stopImportPlayerLog() {
		try {
			Main.importedPlayersFw.close();
		} catch(IOException e) {
			e.printStackTrace();
			Bukkit.getServer().shutdown();
		}
	}

	public static void logImportPlayer(String name) {
		BufferedWriter importedPlayersBw = new BufferedWriter(Main.importedPlayersFw);
		importedPlayersBw.write(name + "\n");
		importedPlayersBw.close();
	}

	public static void removeImportPlayerLog(String name) {
		try {
			Scanner read = new Scanner(Main.importedPlayers);
			ArrayList<String> lines = new ArrayList<String>();

			while(read.hasNextLine()) {
				String line = read.nextLine();
				if(!line.replace("\r","").replace("\n","").equals(name)) lines.add(line);
			}

			read.close();

			Main.importedPlayers.delete();
			Main.importedPlayers.createNewFile();
			BufferedWriter importedPlayersBw = new BufferedWriter(Main.importedPlayersFw);

			for(String line : lines) importedPlayersBw.write(line);

			importedPlayersBw.close();
		} catch(IOException e) {
			e.printStackTrace();
			Bukkit.getServer().shutdown();
		}
	}
	
}
