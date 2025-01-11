package io.github.winnpixie.serversettings;

import io.github.winnpixie.commons.spigot.configurations.Link;

import java.util.List;

public class Config {
    // Max Players
    @Link(path = "server.max-players.override")
    public static boolean OVERRIDE_MAX_PLAYERS;

    @Link(path = "server.max-players.value")
    public static int MAX_PLAYERS;

    // MOTD
    @Link(path = "server.motd.override")
    public static boolean OVERRIDE_MOTD;

    @Link(path = "server.motd.messages")
    public static List<String> MOTDS;

    // PvP
    @Link(path = "server.pvp.override")
    public static boolean OVERRIDE_PVP;

    @Link(path = "server.pvp.allowed")
    public static boolean ALLOW_PVP;

    @Link(path = "server.broadcast-to-lan")
    public static boolean BROADCAST_TO_LAN;
}
