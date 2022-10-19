package me.rickylafleur.homepreventer.utils;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Rayzr
 */
public class MessageHandler {
    private final Map<String, String> messages = new HashMap<>();

    private static String getBaseKey(String key) {
        return key.lastIndexOf('.') < 0 ? "" : key.substring(0, key.lastIndexOf('.'));
    }

    /**
     * Loads all messages from the given config file.
     *
     * @param config The config file to load from.
     */
    public void load(ConfigurationSection config) {
        messages.clear();
        config.getKeys(true).forEach(key -> messages.put(key, config.get(key).toString()));
    }

    /**
     * Fetches the prefix of a given key in the config.
     *
     * @param key The key to get the prefix for.
     * @return The prefix for the given key.
     */
    private String getPrefixFor(String key) {
        final String baseKey = getBaseKey(key);
        final String basePrefix = messages.getOrDefault(baseKey + ".prefix", messages.getOrDefault("prefix", ""));
        final String addon = messages.getOrDefault(baseKey + ".prefix-addon", "");

        return ChatColor.translateAlternateColorCodes('&', basePrefix + addon);
    }

    /**
     * Translates a message from the language file.
     *
     * @param key     The key of the message to translate
     * @param objects The formatting objects to use
     * @return The formatted message
     */
    public String tr(String key, Object... objects) {
        return getPrefixFor(key) + ChatColor.translateAlternateColorCodes('&', String.format(messages.getOrDefault(key, key), objects));
    }
}