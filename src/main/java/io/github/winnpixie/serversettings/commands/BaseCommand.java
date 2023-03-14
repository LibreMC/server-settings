package io.github.winnpixie.serversettings.commands;

import io.github.winnpixie.serversettings.ServerSettings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class BaseCommand implements TabExecutor {
    private final String name;
    private final ServerSettings plugin;

    public BaseCommand(String name, ServerSettings plugin) {
        this.name = name;
        this.plugin = plugin;
    }

    public String getName() {
        return name;
    }

    public ServerSettings getPlugin() {
        return plugin;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }
}
