package io.github.winnpixie.serversettings.commands.admin;

import io.github.winnpixie.commons.spigot.commands.BaseCommand;
import io.github.winnpixie.commons.spigot.commands.CommandErrors;
import io.github.winnpixie.commons.spigot.configurations.adapters.BukkitConfigurationAdapter;
import io.github.winnpixie.serversettings.ServerSettings;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class ServerSettingsCommand extends BaseCommand<ServerSettings> {
    private final BaseComponent reloadedMessage = new ComponentBuilder("Settings reloaded!")
            .color(ChatColor.GREEN)
            .build();
    private final BaseComponent usageMessage = new ComponentBuilder("=== Server-Settings ===")
            .color(ChatColor.GOLD)
            .append("\n/serversettings reload|rl - Reloads the plugin configuration from file.", ComponentBuilder.FormatRetention.NONE)
            .build();

    public ServerSettingsCommand(ServerSettings plugin) {
        super(plugin, "serversettings");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("serversettings.command") && !sender.isOp()) {
            sender.spigot().sendMessage(CommandErrors.LACKS_PERMISSIONS);
            return true;
        }

        if (args.length < 1) {
            sender.spigot().sendMessage(usageMessage);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload":
            case "rl":
                reloadConfiguration(sender);
                break;
            default:
                sender.spigot().sendMessage(CommandErrors.INVALID_ARGUMENTS);
                break;
        }

        return true;
    }

    private void reloadConfiguration(CommandSender sender) {
        if (!sender.hasPermission("serversettings.command.reload") && !sender.isOp()) {
            sender.spigot().sendMessage(CommandErrors.LACKS_PERMISSIONS);
            return;
        }

        this.getPlugin().reloadConfig();

        BukkitConfigurationAdapter adapter = (BukkitConfigurationAdapter) getPlugin().configuration.getAdapter();
        adapter.setConfiguration(getPlugin().getConfig());
        getPlugin().configuration.load();

        sender.spigot().sendMessage(reloadedMessage);
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return Collections.singletonList("reload");
    }
}
