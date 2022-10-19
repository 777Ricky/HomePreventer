package me.rickylafleur.homepreventer;

import me.rickylafleur.homepreventer.command.CommandHomePreventer;
import me.rickylafleur.homepreventer.command.CommandHomeWrapper;
import me.rickylafleur.homepreventer.command.CommandSetHomeWrapper;
import me.rickylafleur.homepreventer.listener.PluginListener;
import me.rickylafleur.homepreventer.utils.CommandWrapper;
import me.rickylafleur.homepreventer.utils.MessageHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

public final class HomePreventer extends JavaPlugin {

    public static final String BYPASS_PERM = "homepreventer.bypass";

    private static HomePreventer instance;
    private MessageHandler messages = new MessageHandler();
    private final Set<CommandWrapper> commandWrapperSet = new HashSet<>();

    public static HomePreventer getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        reload();

        getCommand("homepreventer").setExecutor(new CommandHomePreventer(this));

        getServer().getPluginManager().registerEvents(new PluginListener(this), this);

        hookCommandWrappers();
    }

    @Override
    public void onDisable() {
        unhookCommandWrappers();

        instance = null;
    }

    public void hookCommandWrappers() {
        if (Bukkit.getPluginManager().isPluginEnabled("Essentials")) {
            commandWrapperSet.add(new CommandHomeWrapper(this, Bukkit.getPluginCommand("home")));
            commandWrapperSet.add(new CommandSetHomeWrapper(this, Bukkit.getPluginCommand("sethome")));
        }

        commandWrapperSet.forEach(CommandWrapper::hook);
    }

    public void unhookCommandWrappers() {
        commandWrapperSet.forEach(CommandWrapper::unhook);
        commandWrapperSet.clear();
    }

    public void reload() {
        saveDefaultConfig();
        reloadConfig();

        messages.load(getConfig("messages.yml"));
    }

    public YamlConfiguration getConfig(String path) {
        if (!getFile(path).exists() && getResource(path) != null) {
            saveResource(path, true);
        }
        return YamlConfiguration.loadConfiguration(getFile(path));
    }

    public void saveConfig(YamlConfiguration config, String path) {
        try {
            config.save(getFile(path));
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Failed to save config", e);
        }
    }

    public File getFile(String path) {
        return new File(getDataFolder(), path.replace('/', File.separatorChar));
    }

    public String tr(String key, Object... objects) {
        return messages.tr(key, objects);
    }

    public boolean checkPermission(CommandSender target, String permission, boolean sendMessage) {
        String fullPermission = String.format("%s.%s", getName(), permission);

        if (!target.hasPermission(fullPermission)) {
            if (sendMessage) {
                target.sendMessage(tr("no-permission", fullPermission));
            }

            return false;
        }

        return true;
    }

    public MessageHandler getMessages() {
        return messages;
    }
}
