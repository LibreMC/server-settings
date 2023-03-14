package io.github.winnpixie.serversettings.listeners.impl;

import io.github.winnpixie.hukkit.TextHelper;
import io.github.winnpixie.serversettings.Config;
import io.github.winnpixie.serversettings.ServerSettings;
import io.github.winnpixie.serversettings.listeners.BaseListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.server.ServerListPingEvent;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class ConnectionListener extends BaseListener {
    public ConnectionListener(ServerSettings plugin) {
        super(plugin);

        int port = plugin.getServer().getPort();
        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            if (!Config.BROADCAST_TO_LAN)
                return;

            // According to
            // https://wiki.vg/Server_List_Ping#Ping_via_LAN_.28Open_to_LAN_in_Singleplayer.29
            try (DatagramSocket socket = new DatagramSocket()) {
                var motd = plugin.getServer().getMotd();
                if (Config.MOTDS.size() > 0) {
                    motd = TextHelper.formatColors(Config.MOTDS.get((int) (Math.random() * Config.MOTDS.size())));
                }

                var payload = String.format("[MOTD]%s[/MOTD][AD]%d[/AD]", motd, port).getBytes(StandardCharsets.UTF_8);
                DatagramPacket packet = new DatagramPacket(payload, payload.length,
                        InetAddress.getByName("224.0.2.60"), 4445);
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0, 30);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onPing(ServerListPingEvent event) {
        event.setMaxPlayers(Config.MAX_PLAYERS);

        if (Config.MOTDS.isEmpty())
            return;
        event.setMotd(TextHelper.formatColors(Config.MOTDS.get((int) (Math.random() * Config.MOTDS.size()))));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onLogin(PlayerLoginEvent event) {
        var oldResult = event.getResult();

        if (getPlugin().getServer().getOnlinePlayers().size() >= Config.MAX_PLAYERS) {
            var kickMessage = getPlugin().getServer().spigot().getConfig()
                    .getString("messages.server-full", event.getKickMessage());

            event.disallow(PlayerLoginEvent.Result.KICK_FULL, kickMessage);
            return;
        }

        if (oldResult == PlayerLoginEvent.Result.KICK_FULL)
            event.allow();
    }
}
