package plugins.astro.cartremover;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;


public class RemoveCarts extends JavaPlugin implements Listener
{
	public static Plugin instance;
	public static RemoveCarts plugin;

	@Override
	public void onEnable()
	{
		super.onEnable();
		plugin = this;

		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(this, this);
		instance = this;
	}

	public WorldEditPlugin getWorldEdit()
	{
		Plugin p = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
		if (p instanceof WorldEditPlugin) return (WorldEditPlugin) p;
		else return null;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) 
	{
		if (cmd.getName().equalsIgnoreCase("removecarts"))
		{
			if(sender.hasPermission("removecarts.use"))
			{
				if (!(sender instanceof Player)) 
				{
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bCartRemover&8] &cError: You must be a player, geez console."));
				}
				else
				{
					Player p = (Player) sender;

					Selection s = getWorldEdit().getSelection(p);
					if (s == null) 
					{
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bCartRemover&8] &cError: You must make a selection first."));
					}
					else
					{
						int cartCount = 0;
						for(Entity e : p.getWorld().getEntities())
						{
							if(e instanceof Minecart)
							{
								if(s.contains(e.getLocation()))
								{
									e.remove();
									cartCount++;
								}
							}
						}
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bCartRemover&8] &6You successfully removed &e" + cartCount + " &6minecarts!"));
					}
				}
			}
			else
			{
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&bCartRemover&8] &cError: You have no permission to use this command."));
			}
		}
		return true;
	}
}