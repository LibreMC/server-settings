package io.github.winnpixie.serversettings.listeners.impl;

import io.github.winnpixie.hukkit.MathHelper;
import io.github.winnpixie.hukkit.TextHelper;
import io.github.winnpixie.serversettings.Config;
import io.github.winnpixie.serversettings.ServerSettings;
import io.github.winnpixie.serversettings.listeners.BaseListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.server.ServerListPingEvent;

public class ConnectionListener extends BaseListener {
    public ConnectionListener(ServerSettings plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onPing(ServerListPingEvent event) {
        if (Config.OVERRIDE_MAX_PLAYERS) {
            event.setMaxPlayers(Config.MAX_PLAYERS);
        }

        if (Config.OVERRIDE_MOTD
                && !Config.MOTDS.isEmpty()) {
            event.setMotd(TextHelper.formatColors(Config.MOTDS.get(MathHelper.getRandomInt(0, Config.MOTDS.size()))));
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onLogin(PlayerLoginEvent event) {
        if (!Config.OVERRIDE_MAX_PLAYERS) return;

        var oldResult = event.getResult();

        if (getPlugin().getServer().getOnlinePlayers().size() >= Config.MAX_PLAYERS) {
            var kickMessage = getPlugin().getServer().spigot().getConfig()
                    .getString("messages.server-full", event.getKickMessage());

            event.disallow(PlayerLoginEvent.Result.KICK_FULL, kickMessage);
            return;
        }

        if (oldResult != PlayerLoginEvent.Result.KICK_FULL) return;

        event.allow();
    }
}
