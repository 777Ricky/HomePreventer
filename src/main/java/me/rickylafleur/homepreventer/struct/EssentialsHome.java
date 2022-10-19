package me.rickylafleur.homepreventer.struct;

import com.earth2me.essentials.User;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public class EssentialsHome {

    private final Player player;
    private final String name;
    private final Location location;

    public EssentialsHome(Player player, String name, Location location) {
        this.player = player;
        this.name = name;
        this.location = location;
    }

    public static Optional<EssentialsHome> fromUserHome(User user, String name) {
        try {
            return of(new EssentialsHome(user.getBase(), name, user.getHome(name)));
        } catch (Exception e) {
            return empty();
        }
    }

    public Player getPlayer() {
        return player;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }
}