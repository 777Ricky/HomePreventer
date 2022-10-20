package me.rickylafleur.homepreventer.command;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import me.rickylafleur.homepreventer.HomePreventer;
import me.rickylafleur.homepreventer.struct.EssentialsHome;
import me.rickylafleur.homepreventer.utils.CommandWrapper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static me.rickylafleur.homepreventer.utils.LocationUtil.canHaveHome;

public class CommandHomeWrapper extends CommandWrapper {

    private final HomePreventer plugin;

    public CommandHomeWrapper(HomePreventer plugin, PluginCommand pluginCommand) {
        super(pluginCommand);

        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof final Player player) || sender.hasPermission(HomePreventer.BYPASS_PERM)) {
            return super.onCommand(sender, command, label, args);
        }

        final User user = Essentials.getPlugin(Essentials.class).getUser(player);

        final List<EssentialsHome> homesToRemove = user.getHomes().stream()
                .map(name -> EssentialsHome.fromUserHome(user, name).orElse(null))
                .filter(Objects::nonNull)
                .filter(home -> !canHaveHome(home.getLocation()))
                .toList();

        homesToRemove.forEach(home -> {
            try {
                user.delHome(home.getName());
                player.sendMessage(plugin.tr("event.home-removed", home.getName()));
            } catch (Exception ignored) {

            }
        });

        return super.onCommand(sender, command, label, args);
    }
}