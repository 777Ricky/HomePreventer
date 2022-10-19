package me.rickylafleur.homepreventer.listener;

import me.rickylafleur.homepreventer.HomePreventer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;

public class PluginListener implements Listener {
    private final HomePreventer plugin;

    public PluginListener(HomePreventer plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPluginDisable(PluginDisableEvent e) {
        if (e.getPlugin().getName().equals("Essentials")) {
            plugin.unhookCommandWrappers();
        }
    }

    @EventHandler
    public void onPluginEnable(PluginEnableEvent e) {
        if (e.getPlugin().getName().equals("Essentials")) {
            plugin.hookCommandWrappers();
        }
    }
}