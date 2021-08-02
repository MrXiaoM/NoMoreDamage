package top.mrxiaom.nomoredamage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	public void onEnable() {
		this.saveDefaultConfig();
		this.reloadConfig();
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.isOp()) {
			if(args.length == 1 && args[0].equalsIgnoreCase("reload")) {
				this.reloadConfig();
				sender.sendMessage("§a配置文件已重载");
				return true;
			}
			if(args.length == 3 && args[0].equalsIgnoreCase("set")) {
				boolean showMessage = this.getConfig().getBoolean("is-show-message-to-player");
				boolean showMessageSender = this.getConfig().getBoolean("is-show-message-to-sender");
					int ticks = getInteger(args[2]);
					if(ticks < 0) {
						if(showMessageSender)
						sender.sendMessage(t("no-integer"));
						return true;
					}
					boolean flag = false;
					for(Player p : Bukkit.getOnlinePlayers()) {
						if(p.getName().equalsIgnoreCase(args[1])) {
							p.setNoDamageTicks(ticks);
							if(showMessage) p.sendMessage(t("player").replace("%second%", String.valueOf(ticks / 20.0D)));
							flag = true;
							break;
						}
					}
					if(flag) {
						if(showMessageSender) sender.sendMessage(t("sender"));
					}
					else {
						if(showMessageSender) sender.sendMessage(t("not-online"));
					}
			}
			sender.sendMessage("§e用法:\n§a/nomoredamage set [玩家] [时间] §b设置玩家无敌时间，单位是tick，20tick=1秒\n§a/nomoredamage reload §b重载插件配置文件");
		}
		return true;
	}
	
	private String t(String key) {
		if(!this.getConfig().contains("message." + key)) return "语言键不存在: " + key;
		return ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("message."+key));
	}
	
	private static int getInteger(String str) {
		try {
			return Integer.valueOf(str);
		} catch(NumberFormatException e) {
			return -1;
		}
	}
}
