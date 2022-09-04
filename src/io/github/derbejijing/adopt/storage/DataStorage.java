package io.github.derbejijing.adopt.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.derbejijing.adopt.Main;
import net.md_5.bungee.api.ChatColor;

public class DataStorage {

	private File storage;
	private FileWriter storage_fw;
	private Scanner storage_fr;
	
	private ArrayList<Request> requests;
	private ArrayList<ChildRaisePhase> raise_phases;
	private ArrayList<Grownup> grownups;
	private ArrayList<Contract> contracts;
	private ArrayList<GrownupTimerBossbar> bossbars;
	private ArrayList<GrownupParty> parties;
	
	public SimpleDateFormat sdf;
	public SimpleDateFormat bossbarSdf;
	
	public DataStorage(String filename) throws IOException {
		
		this.requests = new ArrayList<Request>();
		this.raise_phases = new ArrayList<ChildRaisePhase>();
		this.grownups = new ArrayList<Grownup>();
		this.contracts = new ArrayList<Contract>();
		this.bossbars = new ArrayList<GrownupTimerBossbar>();
		this.parties = new ArrayList<GrownupParty>();
		
		this.sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		this.bossbarSdf = new SimpleDateFormat("dd HH:mm:ss");
		
		this.storage = new File(filename);
		
		if(!this.storage.exists()) {
			this.storage.createNewFile();
			System.out.println("Data storage created");
		}
		
	}
	
	
	public void bossbar_reenable(String playername) {
		for(GrownupTimerBossbar g : this.bossbars) if(g.playerName.equals(playername)) g.enableBossbar();
	}

	
	public void bossbar_reenable_all() {
		for(GrownupTimerBossbar g : this.bossbars) g.enableBossbar();
	}
	
	
	public void bossbar_setTitle(String name, String title) {
		for(GrownupTimerBossbar g : this.bossbars) if(g.playerName.equals(name)) g.setTitle(title);
	}
	
	
	public void bossbar_toggleColor(String name) {
		for(GrownupTimerBossbar g : this.bossbars) if(g.playerName.equals(name)) g.toggleColor();
	}
	
	
	public boolean child_add(String name) {
		String owner_1 = "";
		boolean found = false;
		
		for(Contract c : this.contracts) {
			if(c.name.equals(name)) {
				owner_1 = c.owner_1;
				found = true;
			}
		}
		
		if(!found) {
			return false;
		}
		
		this.request_remove(owner_1);
		
		return true;
	}
	
	
	public void contract_remove(String name) {
		Contract to_remove = null;
		for(Contract c : this.contracts) if(c.name.equals(name)) to_remove = c;
		this.contracts.remove(to_remove);
	}
	
	
	public void crp_add(String name) {
		Contract contract = null;
		for(Contract c : this.contracts) if(c.name.equals(name)) contract = c;
		if(contract == null) {
			System.out.println("addChiltRaisePhase failed");
			return;
		}
		
		this.contract_remove(name);
		
		this.raise_phases.add(new ChildRaisePhase(name, contract.owner_1, contract.owner_2));
		
		GrownupTimerBossbar gtb = new GrownupTimerBossbar(name);
		gtb.enableBossbar();
		this.bossbars.add(gtb);
	}
	
	
	public Date crp_getGrownupDate(String name) {
		for(ChildRaisePhase c : this.raise_phases) if(c.name.equals(name)) return c.getGrownupDate();
		return new Date();
	}
	
	
	public String crp_getGrownupDateFormatted(String name) {
		for(ChildRaisePhase c : this.raise_phases) if(c.name.equals(name)) return this.bossbarSdf.format(c.getGrownupDate());
		return "";
	}
	
	
	public double crp_getProgress(String name) {
		for(ChildRaisePhase c : this.raise_phases) if(c.name.equals(name)) return c.getProgress();
		return 1;
	}
	
	
	public void crp_remove(String name) {
		ChildRaisePhase to_remove = null;
		for(ChildRaisePhase c : this.raise_phases) if(c.name.equals(name)) to_remove = c;
		this.raise_phases.remove(to_remove);
	}
	
	
	public String getParrent_1(String name) {
		for(ChildRaisePhase c : this.raise_phases) {
			if(c.name.equals(name)) return c.owner_1;
		}
		
		for(Grownup g : this.grownups) {
			if(g.name.equals(name)) return g.owner_1;
		}
		
		for(Contract c : this.contracts) {
			if(c.name.endsWith(name)) return c.owner_1;
		}
		
		return "?";
	}
	
	
	public String getParrent_2(String name) {
		for(ChildRaisePhase c : this.raise_phases) {
			if(c.name.equals(name)) return c.owner_2;
		}
		
		for(Grownup g : this.grownups) {
			if(g.name.equals(name)) return g.owner_2;
		}
		
		for(Contract c : this.contracts) {
			if(c.name.endsWith(name)) return c.owner_1;
		}
		
		return "?";
	}
	
	
	public void grownup_add(String name) {
		this.grownups.add(new Grownup(name, "?", "?"));
	}
	
	
	public Date grownup_getDate(String name) {	
		for(ChildRaisePhase c : this.raise_phases) {
			if(c.name.equals(name)) return c.getGrownupDate();
		}
		
		return null;
	}

	
	public boolean playerIsBeingCelebrated(String name) {
		for(GrownupParty g : this.parties) if(g.playername.equals(name)) return true;
		return false;
	}
	
	
	public boolean playerIsBeingRaised(String name) {
		for(ChildRaisePhase c : this.raise_phases) if(c.name.equals(name)) return true;
		return false;
	}
	
	
	public boolean playerIsProperty(String name) {
		for(ChildRaisePhase c : this.raise_phases) if(c.name.equals(name)) return true;
		for(Contract c : this.contracts) if(c.name.equals(name)) return true;
		return false;
	}

	public boolean playerIsRaisingChild(String name) {
		for(Contract c : this.contracts) if(c.owner_1.equals(name) || c.owner_2.equals(name)) return true;
		for(ChildRaisePhase crp : this.raise_phases) if(crp.owner_1.equals(name) || crp.owner_2.equals(name)) return true;
		return false;
	}
	
	public void request_accept(String player_from, String player_to) {	
		String player_to_adopt = "";
		Request to_remove = null;
		
		for(Request r : this.requests) {
			if(r.player_from.equals(player_from) && r.player_to.equals(player_to)) {
			
				player_to_adopt = r.player_to_adopt;		
				to_remove = r;
				
			}		
			
		}
		
		this.requests.remove(to_remove);
		
		Player from = Bukkit.getPlayer(player_from);
		from.sendMessage(ChatColor.GOLD + player_to + ChatColor.GRAY + " accepted your request!");
		this.contracts.add(new Contract(player_to_adopt, player_from, player_to));
	}
	
	public int request_add(String player_request_from, String player_request_to, String adopt_target) {
		
		boolean request_target_grownup = false;
		
		for(Request r : this.requests) {
			// cannot make multiple requests at the same time
			if(r.player_from.equals(player_request_from)) return -1;
			if(r.player_from.equals(player_request_to) && r.player_to.equals(player_request_from) && r.player_to_adopt.equals(adopt_target)) return -6;
		}
		
		for(Contract c : this.contracts) {
			// someone has already adopted the target
			if(c.name.equals(adopt_target)) return -2;
		}
		
		for(Grownup g : this.grownups) {
			// target is already a grownup
			if(g.name.equals(adopt_target)) return -3;
			if(g.name.equals(player_request_to)) request_target_grownup = true;
		}
		
		if(request_target_grownup == false) return -4;
		if(player_request_from == player_request_to || player_request_to == adopt_target || player_request_from == adopt_target) return -5;
		
		this.requests.add(new Request(player_request_from, player_request_to, adopt_target));
		
		return 0;
	}
	
	
	public void request_clearRequests(String player) {
		for(Request r : this.requests) {
			if(r.player_from.equals(player)) this.requests.remove(r);
		}
	}
	
	
	public void request_decline(String player_from, String player_to) {
		Request to_remove = null;
		for(Request r : this.requests) if(r.player_from.equals(player_from) && r.player_to.equals(player_to)) to_remove = r;
		this.requests.remove(to_remove);
		
		Player from = Bukkit.getPlayer(player_from);
		if(from == null) return;
		
		from.sendMessage(ChatColor.GOLD + player_to + ChatColor.RED + " declined your request!");
	}
	
	public boolean request_exists(String player_from, String player_to) {
		for(Request r : this.requests) System.out.println(r.player_from + " " + r.player_to);
		
		for(Request r : this.requests) if(r.player_from.equals(player_from) && r.player_to.equals(player_to)) return true;
		return false;
	}
	
	
	public void request_remove(String player_from) {
		Request to_remove = null;
		for(Request r : this.requests) if(r.player_from.equals(player_from)) to_remove = r;
		this.requests.remove(to_remove);
	}
	
	
	public void tick() {
		ArrayList<Request> removeRequests = new ArrayList<Request>();
		ArrayList<ChildRaisePhase> removeChilds = new ArrayList<ChildRaisePhase>();
		ArrayList<GrownupParty> removeParties = new ArrayList<GrownupParty>();
		ArrayList<GrownupTimerBossbar> removeBossbars = new ArrayList<GrownupTimerBossbar>();
		
		
		for(Request r : this.requests) if(r.timeout_reached()) {
			removeRequests.add(r);
		}
		
		for(ChildRaisePhase c : this.raise_phases) if(c.grownup()) {
			String name = c.name;
			String owner_1 = c.owner_1;
			String owner_2 = c.owner_2;
			removeChilds.add(c);
			
			this.grownups.add(new Grownup(name, owner_1, owner_2));
			this.parties.add(new GrownupParty(name));
			
			for(Player player : Bukkit.getOnlinePlayers()) {
				if(player.getName().equals(owner_1) || player.getName().equals(owner_2)) {
					player.sendMessage(ChatColor.GREEN + "-----------------------------------------------");
					player.sendMessage(ChatColor.GRAY + "Your child " + ChatColor.GOLD + name + ChatColor.GRAY + " is now a grownup!");
					player.sendMessage(ChatColor.GRAY + "It is officially no longer your property");
					player.sendMessage(ChatColor.GREEN + "-----------------------------------------------");
				}
			}	
		} else {
			Player player = Bukkit.getPlayer(c.name);
			if(player != null) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20, 1));
				player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 20, 1));
			}
		}
		
		for(GrownupParty g : this.parties) {
			if(g.over()) {
				
				removeParties.add(g);
				for(GrownupTimerBossbar gb : this.bossbars) if(gb.playerName.equals(g.playername)) {
					gb.destroy();
					removeBossbars.add(gb);
				}
			}
			else g.tick();
		}
		
		for(GrownupTimerBossbar g : this.bossbars) {
			g.tickBossbar();
		}
		
		this.requests.removeAll(removeRequests);
		this.raise_phases.removeAll(removeChilds);
		this.parties.removeAll(removeParties);
		this.bossbars.removeAll(removeBossbars);
		
	}
	
	
	public void data_load() throws FileNotFoundException {
		System.out.println(ChatColor.GREEN + "loading data from file");
		
		this.storage_fr = new Scanner(this.storage);
		
		while(this.storage_fr.hasNextLine()) {
			String line = this.storage_fr.nextLine();
			
			this.data_parse(line);
			
			/*
			String unspaced = line.replace(" ", "");
			int spaces = line.length() - unspaced.length();
			
			String[] data = line.split(" ");
			
			System.out.println(line);
			
			if(line.startsWith("RAISE") && spaces == 5) {
				this.raise_phases.add(new ChildRaisePhase(data[1], data[2], data[3], data[4], data[5]));
			}
			
			if(line.startsWith("CONTRACT") && spaces == 3) {
				this.contracts.add(new Contract(data[1], data[2], data[3]));
			}
			
			if(line.startsWith("GROWNUP") && spaces == 3) {
				this.grownups.add(new Grownup(data[1], data[2], data[3]));
			}
			
			if(line.startsWith("BOSSBAR") && spaces == 1) {
				this.bossbars.add(new GrownupTimerBossbar(data[1]));
			}
			
			if(line.startsWith("REQUEST") && spaces == 4) {
				this.requests.add(new Request(data[1], data[2], data[3], data[4])); 
			}
			*/
		}
		
		this.storage_fr.close();
		
		System.out.println(ChatColor.GREEN + "loaded all data");
	}
	
	
	public boolean data_parse(String instruction) {
		String unspaced = instruction.replace(" ", "");
		int spaces = instruction.length() - unspaced.length();
		String data[] = instruction.split(" ");
		
		if(instruction.startsWith("RAISE") && spaces == 5) {
			this.raise_phases.add(new ChildRaisePhase(data[1], data[2], data[3], data[4], data[5]));
		}
		else if(instruction.startsWith("CONTRACT") && spaces == 3) {
			this.contracts.add(new Contract(data[1], data[2], data[3]));
		}
		else if(instruction.startsWith("GROWNUP") && spaces == 3) {
			this.grownups.add(new Grownup(data[1], data[2], data[3]));
		}
		else if(instruction.startsWith("BOSSBAR") && spaces == 1) {
			this.bossbars.add(new GrownupTimerBossbar(data[1]));
		}
		else if(instruction.startsWith("REQUEST") && spaces == 4) {
			this.requests.add(new Request(data[1], data[2], data[3], data[4])); 
		}
		else return false;
		
		return true;
	}
	
	
	public void data_save() throws IOException {
		System.out.println(ChatColor.GREEN + "saving all data");
			
		this.storage.delete();
		this.storage.createNewFile();
		this.storage_fw = new FileWriter(this.storage);
		
		for(Request r : this.requests) data_writeLine(r.getString());
		for(ChildRaisePhase crp : this.raise_phases) data_writeLine(crp.getString());
		for(Grownup g : this.grownups) data_writeLine(g.getString());
		for(Contract c : this.contracts) data_writeLine(c.getString());
		for(GrownupTimerBossbar gb : this.bossbars) data_writeLine(gb.getString());
		
		this.storage_fw.close();
		
		System.out.println(ChatColor.GREEN + "saved all data");
	}
	
	
	public void data_writeLine(String line) throws IOException {
		this.storage_fw.write(line + "\n");
	}
	
	
	public void close() throws IOException {
		for(GrownupTimerBossbar gtb : this.bossbars) gtb.destroy();
		this.data_save();
	}
	
}
