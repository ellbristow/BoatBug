package me.ellbristow.BoatBug;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

public class BoatListener extends PlayerListener {

	public static BoatBug plugin;
	
	public BoatListener (BoatBug instance) {
		
	}
	
	public void onPlayerInteract (PlayerInteractEvent event) {
		Action action = event.getAction();
		Player player = event.getPlayer();
		if (action == Action.RIGHT_CLICK_BLOCK && player.getItemInHand().getTypeId() == 333) {
			Block target = event.getClickedBlock();
			if (target.getTypeId() != 8 && target.getTypeId() != 9 && !player.hasPermission("boatbug.bypass")) {
				player.sendMessage(ChatColor.DARK_RED + "You can only place a boat on water!");
				event.setCancelled(true);
			}
		}
	}
	
}
