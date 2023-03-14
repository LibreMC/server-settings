package io.github.winnpixie.serversettings;

import io.github.winnpixie.hukkit.configs.Link;

import java.util.List;

public class Config {
    @Link(path = "server.max-players")
    public static int MAX_PLAYERS;

    @Link(path = "server.messages-of-the-day")
    public static List<String> MOTDS;

    @Link(path = "server.broadcast-to-lan")
    public static boolean BROADCAST_TO_LAN;

    @Link(path = "server.allow-pvp")
    public static boolean ALLOW_PVP;
}
