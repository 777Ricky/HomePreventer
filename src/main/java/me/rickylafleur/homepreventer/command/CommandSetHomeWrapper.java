package me.rickylafleur.homepreventer.command;

import me.rickylafleur.homepreventer.HomePreventer;
import me.rickylafleur.homepreventer.utils.CommandWrapper;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import static me.rickylafleur.homepreventer.utils.LocationUtil.canHaveHome;

public class CommandSetHomeWrapper extends CommandWrapper {

    private final HomePreventer plugin;

    public CommandSetHomeWrapper(HomePreventer plugin, PluginCommand pluginCommand) {
        super(pluginCommand);

        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player) || sender.hasPermission(HomePreventer.BYPASS_PERM)) {
            return super.onCommand(sender, command, label, args);
        }

        final Player player = (Player) sender;
        final Location location = player.getLocation();

        if (!canHaveHome(location)) {
            player.sendMessage(plugin.tr("event.sethome-prevented"));
            return true;
        }

        return super.onCommand(sender, command, label, args);
    }
}