package me.rickylafleur.homepreventer.command;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class CommandHomeTabCompletion implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof final Player player)) return Collections.emptyList();

        final User user = Essentials.getPlugin(Essentials.class).getUser(player);

        if (user == null) return Collections.emptyList();

        for (final String arg : args) {
            if (arg.contains(":")) {
                final String[] split = arg.split(":");

                final User target = Essentials.getPlugin(Essentials.class).getUser(split[0]);

                if (target == null) return Collections.emptyList();

                return target.getHomes().stream().map(s -> target.getName() + ":" + s).toList();
            }
        }

        return user.getHomes();
    }
}