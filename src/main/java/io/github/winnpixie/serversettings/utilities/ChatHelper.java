package io.github.winnpixie.serversettings.utilities;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class ChatHelper {
    public static final BaseComponent[] NO_PERMISSION = new ComponentBuilder("Insufficient permissions.")
            .color(ChatColor.RED)
            .create();
}
