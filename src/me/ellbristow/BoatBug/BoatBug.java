package me.ellbristow.BoatBug;

import java.util.List;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class BoatBug extends JavaPlugin {
	
	public static BoatBug plugin;
	public final Logger logger = Logger.getLogger("Minecraft");
	public final BoatListener boatListener = new BoatListener(this);
	
	@Override
	public void onDisable() {
	}
	
	@Override
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(boatListener, this);
	}
	
        @Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args ) {
		if (commandLabel.equalsIgnoreCase("clearboats")) {
			if (sender instanceof Player) {
				// Player command
				World world = ((Player) sender).getWorld();
				clearBoats(sender, world);
				return true;
			}
			else {
				// Console command (requires [world])
				if (args.length != 1) {
					sender.sendMessage("Usage: clearboats [world]");
					return true;
				}
				else {
					World world = getServer().getWorld(args[0]);
					if (world == null) {
						sender.sendMessage("[ERROR] World '" + args[0] + "' not found!");
						sender.sendMessage("Usage: clearboats [world]");
						return true;
					}
					else {
						clearBoats(sender, world);
						return true;
					}
				}
			}

		}
		return false;
	}
	
	public void clearBoats(CommandSender sender, World world) {
		List<Entity> entities = world.getEntities();
		int count = 0;
		int fail = 0;
		int pass = 0;
		for (Entity entity : entities) {
			if (entity.getClass().getSimpleName().toString().equals("CraftBoat")) {
				count++;
				if (entity.getPassenger() != null) {
					fail++;
				}
				else {
					entity.remove();
					pass++;
				}
			}
		}
		if (count == 0) {
			sender.sendMessage("No boats found to clear in world '" + ChatColor.GOLD + world.getName() + ChatColor.WHITE + "'!");
		}
		else {
			if (pass != 0) {
				sender.sendMessage(ChatColor.GREEN + "Cleared " + pass + " boats!");
			}
			if (fail != 0) {
				sender.sendMessage(ChatColor.RED + "" + pass + " boats had passengers and were not cleared!");
			}
		}
	}
	
}
