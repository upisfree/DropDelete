package lemon42.DropDelete;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DropDelete extends JavaPlugin implements Listener {
	Logger log;
	List<String> players;
	
	@Override
	public void onEnable() {
		log = this.getLogger();
		players = new ArrayList<String>();
		
		getServer().getPluginManager().registerEvents(this, this);
		
		log.info("Version " + getDescription().getVersion() + " enabled!");
	}
	@Override
	public void onDisable() {
		log.info("Version " + getDescription().getVersion() + " disabled.");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player player = (sender instanceof Player ? (Player)sender : null); //replaces old if :L
		
		if (cmd.getName().equalsIgnoreCase("del")) {
			if(player != null) {
				if(player.hasPermission("DropDelete.use")) {
					if(players.contains(player.getName())) {
						players.remove(player.getName());
						player.sendMessage(ChatColor.GREEN + "Your items will no longer delete when dropped.");
					} else {
						players.add(player.getName());
						player.sendMessage(ChatColor.GREEN + "Your items will now delete when dropped.");
					}
				} else player.sendMessage(ChatColor.RED + "You don't have sufficient permissions to use this command.");
			} else sender.sendMessage(ChatColor.RED + "Only players may use this command.");
		}
		return true;
	}
	
	//Event listener
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
	public void onDrop(PlayerDropItemEvent event) {
		if(players.contains(event.getPlayer().getName()))
			event.getItemDrop().remove();
	}
}
