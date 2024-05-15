package io.github.winnpixie.serversettings.listeners;

import io.github.winnpixie.commons.spigot.MathHelper;
import io.github.winnpixie.commons.spigot.TextHelper;
import io.github.winnpixie.commons.spigot.listeners.EventListener;
import io.github.winnpixie.serversettings.Config;
import io.github.winnpixie.serversettings.ServerSettings;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.server.ServerListPingEvent;

public class ConnectionListener extends EventListener<ServerSettings> {
    public ConnectionListener(ServerSettings plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onPing(ServerListPingEvent event) {
        if (Config.OVERRIDE_MAX_PLAYERS) event.setMaxPlayers(Config.MAX_PLAYERS);

        if (Config.OVERRIDE_MOTD && !Config.MOTDS.isEmpty()) {
            event.setMotd(TextHelper.formatText(Config.MOTDS.get(MathHelper.getRandomInt(0, Config.MOTDS.size()))));
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onLogin(PlayerLoginEvent event) {
        if (!Config.OVERRIDE_MAX_PLAYERS) return;

        PlayerLoginEvent.Result originalResult = event.getResult();

        if (getPlugin().getServer().getOnlinePlayers().size() >= Config.MAX_PLAYERS) {
            String reason = getPlugin().getServer().spigot().getConfig()
                    .getString("messages.server-full", event.getKickMessage());

            event.disallow(PlayerLoginEvent.Result.KICK_FULL, reason);
            return;
        }

        if (originalResult == PlayerLoginEvent.Result.KICK_FULL) event.allow();
    }
}
