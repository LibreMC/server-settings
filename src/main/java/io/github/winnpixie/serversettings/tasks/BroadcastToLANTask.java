package io.github.winnpixie.serversettings.tasks;

import io.github.winnpixie.hukkit.MathHelper;
import io.github.winnpixie.hukkit.TextHelper;
import io.github.winnpixie.serversettings.Config;
import io.github.winnpixie.serversettings.ServerSettings;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class BroadcastToLANTask implements Runnable {
    private final ServerSettings plugin;
    private final int port;

    public BroadcastToLANTask(ServerSettings plugin) {
        this.plugin = plugin;

        this.port = plugin.getServer().getPort();
    }

    @Override
    public void run() {
        if (!Config.BROADCAST_TO_LAN) return;

        // https://wiki.vg/Server_List_Ping#Ping_via_LAN_.28Open_to_LAN_in_Singleplayer.29
        try (DatagramSocket socket = new DatagramSocket()) {
            var motd = plugin.getServer().getMotd();

            if (Config.OVERRIDE_MOTD
                    && !Config.MOTDS.isEmpty()) {
                motd = TextHelper.formatColors(Config.MOTDS.get(MathHelper.getRandomInt(0, Config.MOTDS.size())));
            }

            var payload = "[MOTD]%s[/MOTD][AD]%d[/AD]".formatted(motd, port).getBytes(StandardCharsets.UTF_8);
            DatagramPacket packet = new DatagramPacket(payload, payload.length,
                    InetAddress.getByName("224.0.2.60"), 4445);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
