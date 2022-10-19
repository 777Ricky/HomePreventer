package me.rickylafleur.homepreventer.utils;

import me.rickylafleur.homepreventer.HomePreventer;
import org.bukkit.Location;

public class LocationUtil {

    private LocationUtil() {}

    public static boolean canHaveHome(Location location) {
        return HomePreventer.getInstance().getConfig().getStringList("worlds").stream()
                .noneMatch(s -> s.equals(location.getWorld().getName()));
    }
}
