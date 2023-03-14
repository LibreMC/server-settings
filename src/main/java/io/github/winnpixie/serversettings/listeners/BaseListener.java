package io.github.winnpixie.serversettings.listeners;

import io.github.winnpixie.serversettings.ServerSettings;
import org.bukkit.event.Listener;

public class BaseListener implements Listener {
    private final ServerSettings plugin;

    public BaseListener(ServerSettings plugin) {
        this.plugin = plugin;
    }

    public ServerSettings getPlugin() {
        return plugin;
    }
}
